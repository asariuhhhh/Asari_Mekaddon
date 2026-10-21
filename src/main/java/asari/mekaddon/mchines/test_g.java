package asari.mekaddon.mchines;

import asari.mekaddon.block.AAblocks;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.ItemStackToGasRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.computercraft.ComputerConstants;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.chemical.GasInventorySlot;
import mekanism.common.inventory.warning.WarningTracker;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.prefab.TileEntityProgressMachine;
import mekanism.common.util.MekanismUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.IFluidTank;
import org.jetbrains.annotations.NotNull;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;

import java.util.List;
import org.jetbrains.annotations.Nullable;
import mekanism.api.recipes.inputs.InputHelper;

public class test_g extends TileEntityProgressMachine<ItemStackToGasRecipe> implements ISingleRecipeLookupHandler.ItemRecipeLookupHandler<ItemStackToGasRecipe> {
    public IGasTank InputGasTank;
    public IFluidTank InputFluidTank;
    public IGasTank gasTank;
    InputInventorySlot inputSlot;
    GasInventorySlot outputSlot;
    EnergyInventorySlot energySlot;
    private final IOutputHandler<@NotNull GasStack> outputHandler;
    private final IInputHandler<@NotNull ItemStack> inputHandler;
    private MachineEnergyContainer<test_g> energyContainer;
    private static final long MAX_GAS = 114514_000;


    private static final List<CachedRecipe.OperationTracker.RecipeError> TRACKED_ERROR_TYPES = List.of(
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );

    public test_g(BlockPos pos,BlockState state) {
        super(AAblocks.TEST_G,pos,state,TRACKED_ERROR_TYPES,1);



        //機械のconfig
        configComponent = new TileComponentConfig(this, TransmissionType.ITEM,TransmissionType.GAS,TransmissionType.ENERGY);
        configComponent.setupItemIOConfig(inputSlot,outputSlot,energySlot);
        configComponent.setupOutputConfig(TransmissionType.GAS,gasTank, RelativeSide.RIGHT);
        configComponent.setupInputConfig(TransmissionType.ENERGY,energyContainer);

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent,TransmissionType.GAS);

        inputHandler = InputHelper.getInputHandler(inputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(gasTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE);

    }

    @NotNull
    @Override
    public IChemicalTankHolder<Gas, GasStack, IGasTank> getInitialGasTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper<Gas, GasStack, IGasTank> builder = ChemicalTankHelper.forSideGasWithConfig(this::getDirection, this::getConfig);
        builder.addTank(gasTank = ChemicalTankBuilder.GAS.output(MAX_GAS, listener));
        return builder.build();
    }
    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener, IContentsListener recipeCacheListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this,listener));
        return builder.build();
    }
    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addSlot(inputSlot = InputInventorySlot.at(this::containsRecipe, recipeCacheListener, 26, 36))
                .tracksWarnings(slot -> slot.warning(WarningTracker.WarningType.NO_MATCHING_RECIPE, getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(outputSlot = GasInventorySlot.drain(gasTank, listener, 152, 55));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 152, 14));
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }
    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        outputSlot.drainTank();
        recipeCacheLookupMonitor.updateAndProcess();
    }

    @NotNull
    @Override
    public IMekanismRecipeTypeProvider<ItemStackToGasRecipe,SingleItem<ItemStackToGasRecipe>> getRecipeType() {
        return MekanismRecipeType.OXIDIZING;
    }
    @Nullable
    @Override
    public ItemStackToGasRecipe getRecipe(int cachIndex) {
        return findFirstRecipe(inputHandler);
    }
    @NotNull
    @Override
    public CachedRecipe<ItemStackToGasRecipe> createNewCachedRecipe(@NotNull ItemStackToGasRecipe recipe,int cachIndex) {
        return OneInputCachedRecipe.itemToChemical(recipe,recheckAllRecipeErrors,inputHandler,outputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(()-> MekanismUtils.canFunction(this))
                .setActive(this::setActive)
                .setEnergyRequirements(energyContainer::getEnergyPerTick,energyContainer)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setBaselineMaxOperations(this::getBaselineMaxOperations);
                //.setOperatingTicksChanged(this::setOperatingTicks);
    }
    protected int getBaselineMaxOperations() {
        return 114514;
    }


    public MachineEnergyContainer<test_g> getEnergyContainer() {
        return energyContainer;
    }
    //Methods relating to IComputerTile
    @ComputerMethod(methodDescription = ComputerConstants.DESCRIPTION_GET_ENERGY_USAGE)
    FloatingLong getEnergyUsage() {
        return getActive() ? energyContainer.getEnergyPerTick() : FloatingLong.ZERO;
    }
    //End methods IComputerTile

}
