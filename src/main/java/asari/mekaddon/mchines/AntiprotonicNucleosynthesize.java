package asari.mekaddon.mchines;

import asari.api.recipes.AntiprotonicNucleosynthesizeRecipe;
import asari.mekaddon.block.AAblocks;
import asari.mekaddon.prefab.AsariMekAddonTileEntityRecipeMachine;
import asari.mekaddon.recipe.AsariMekAddonRecipTypeProvider;
import asari.mekaddon.recipe.AsariMekAddonRecipeType;
import asari.mekaddon.recipe.lookup.AsariMekAddonRecipeLookupHandler;
import asari.mekaddon.recipe.lookup.AsariMekAddonSingleRecipeLookupHandler;
import asari.mekaddon.recipe.lookup.cache.AsariMekAddonDoubleSameInputRecipeCache;
import asari.mekaddon.recipe.lookup.cache.AsariMekAddonInputRecipeCache;
import asari.mekaddon.recipe.lookup.cache.AsariMekAddonSingleInputRecipeCache;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.config.MekanismConfig;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.chemical.GasInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.tile.base.SubstanceType;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AntiprotonicNucleosynthesize extends AsariMekAddonTileEntityRecipeMachine<AntiprotonicNucleosynthesizeRecipe> implements
        AsariMekAddonSingleRecipeLookupHandler.ChemicalRecipeLookupHandler<AntiprotonicNucleosynthesizeRecipe> {

    private static final List<CachedRecipe.OperationTracker.RecipeError> TRACKED_ERROR_TYPES = List.of(
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );
    public static final long MAX_GAS = 10_000;
    public IGasTank inputTank;
    public IGasTank outputTank;
    private float peakProductionRate;
    private float productionRate;
    private boolean settingsChecked;
    private boolean needsRainCheck;

    private final IOutputHandler<@NotNull GasStack> outputHandler;
    private final IInputHandler<@NotNull GasStack> inputHandler;
    GasInventorySlot inputSlot;
    GasInventorySlot outputSlot;


    public AntiprotonicNucleosynthesize(BlockPos pos, BlockState state) {
        super(AAblocks.ANTIPROTONIC_NUCLEOSYNTHESIZE, pos, state, TRACKED_ERROR_TYPES);
        configComponent = new TileComponentConfig(this, TransmissionType.ITEM, TransmissionType.GAS);
        configComponent.setupIOConfig(TransmissionType.ITEM, inputSlot, outputSlot, RelativeSide.FRONT);
        configComponent.setupIOConfig(TransmissionType.GAS, inputTank, outputTank, RelativeSide.FRONT, false, true).setEjecting(true);
        configComponent.addDisabledSides(RelativeSide.TOP);

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.GAS)
                .setCanTankEject(tank -> tank != inputTank);
        inputHandler = InputHelper.getInputHandler(inputTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @NotNull
    @Override
    public IChemicalTankHolder<Gas, GasStack, IGasTank> getInitialGasTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper<Gas, GasStack, IGasTank> builder = ChemicalTankHelper.forSideGasWithConfig(this::getDirection, this::getConfig);
        //Allow extracting out of the input gas tank if it isn't external OR the output tank is empty AND the input is radioactive
        builder.addTank(inputTank = ChemicalTankBuilder.GAS.create(MAX_GAS, ChemicalTankHelper.radioactiveInputTankPredicate(() -> outputTank),
                ChemicalTankBuilder.GAS.alwaysTrueBi, this::containsRecipe, ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheListener));

        builder.addTank(outputTank = ChemicalTankBuilder.GAS.output(MAX_GAS, listener));
        return builder.build();
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addSlot(inputSlot = GasInventorySlot.fill(inputTank, listener, 5, 56));
        builder.addSlot(outputSlot = GasInventorySlot.drain(outputTank, listener, 155, 56));
        inputSlot.setSlotType(ContainerSlotType.INPUT);
        inputSlot.setSlotOverlay(SlotOverlay.MINUS);
        outputSlot.setSlotType(ContainerSlotType.OUTPUT);
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    private void recheckSettings() {
        Level world = getLevel();
        if (world == null) {
            return;
        }
        BlockPos pos = getBlockPos();
        Biome b = world.getBiomeManager().getBiome(pos).value();
        needsRainCheck = b.getPrecipitationAt(pos) != Biome.Precipitation.NONE;
        // Consider the best temperature to be 0.8; biomes that are higher than that
        // will suffer an efficiency loss (semiconductors don't like heat); biomes that are cooler
        // get a boost. We scale the efficiency to around 30% so that it doesn't totally dominate
        //float tempEff = 0.3F * (0.8F - b.getTemperature(pos));

        // Treat rainfall as a proxy for humidity; any humidity works as a drag on overall efficiency.
        // As with temperature, we scale it so that it doesn't overwhelm production. Note the signedness
        // on the scaling factor. Also note that we only use rainfall as a proxy if it CAN rain; some dimensions
        // (like the End) have rainfall set, but can't actually support rain.
        float humidityEff = needsRainCheck ? -0.3F * b.getModifiedClimateSettings().downfall() : 0.0F;
        peakProductionRate = MekanismConfig.general.maxSolarNeutronActivatorRate.get() * (1.0F + humidityEff);
        settingsChecked = true;
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        if (!settingsChecked) {
            recheckSettings();
        }
        inputSlot.fillTank();
        outputSlot.drainTank();
        productionRate = recalculateProductionRate();
        recipeCacheLookupMonitor.updateAndProcess();
    }

    @NotNull
    @Override
    public AsariMekAddonRecipTypeProvider<AntiprotonicNucleosynthesizeRecipe,AsariMekAddonInputRecipeCache.SingleChemical<AntiprotonicNucleosynthesizeRecipe>> getAMAType() {
        return AsariMekAddonRecipeType.ANTIPROTONIC_NUCLEOSYNTHESIZE;
    }

    @Nullable
    @Override
    public AntiprotonicNucleosynthesizeRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandler);
    }

    @ComputerMethod
    boolean canSeeSun() {
        return WorldUtils.canSeeSun(level, worldPosition.above());
    }

    private boolean canFunction() {
        // Sort out if the solar neutron activator can see the sun; we no longer check if it's raining here,
        // since under the new rules, we can still function when it's raining, albeit at a significant penalty.
        return MekanismUtils.canFunction(this) && !canSeeSun();
    }

    private float recalculateProductionRate() {
        Level world = getLevel();
        if (world == null || !canFunction()) {
            return 0;
        }
        //Get the brightness of the sun; note that there are some implementations that depend on the base
        // brightness function which doesn't take into account the fact that rain can't occur in some biomes.
        float brightness = WorldUtils.getSunBrightness(world, 1.0F);
        //Production is a function of the peak possible output in this biome and sun's current brightness
        float production = peakProductionRate * brightness;
        //If the solar neutron activator is in a biome where it can rain, and it's raining penalize production by 80%
        if (needsRainCheck && (world.isRaining() || world.isThundering())) {
            production *= 0.2F;
        }
        return production;
    }

    @NotNull
    @Override
    public CachedRecipe<AntiprotonicNucleosynthesizeRecipe> createNewCachedRecipe(@NotNull AntiprotonicNucleosynthesizeRecipe recipe, int cacheIndex) {
        return OneInputCachedRecipe.chemicalToChemical(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setOnFinish(this::markForSave)
                //Edge case handling, this should almost always end up being 1
                .setRequiredTicks(() -> productionRate > 0 && productionRate < 1 ? (int) Math.ceil(1 / productionRate) : 1)
                .setBaselineMaxOperations(() -> productionRate > 0 && productionRate < 1 ? 1 : (int) productionRate);
    }

    @Override
    public int getRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(inputTank.getStored(), inputTank.getCapacity());
    }

    @Override
    protected boolean makesComparatorDirty(@Nullable SubstanceType type) {
        return type == SubstanceType.GAS;
    }
}


