package asari.mekaddon.recipe;

import asari.api.recipes.AntiprotonicNucleosynthesizeRecipe;
import asari.api.recipes.AntiprotonicNucleosynthesizeRecipeCate;
import asari.mekaddon.AsariMekAddon;
import asari.mekaddon.recipe.lookup.cache.AsariMekAddonInputRecipeCache;
import asari.mekaddon.recipe.lookup.cache.AsariMekAddonSingleInputRecipeCache;
import asari.mekaddon.regg.AsariMekAddonRecipeTypeDefrredRegister;
import asari.mekaddon.regg.AsariMekAddonRecipeTypeRegistryObject;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.chemical.ChemicalToChemicalRecipe;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.RecipeTypeDeferredRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AsariMekAddonRecipeType<RECIPE extends MekanismRecipe,INPUT_CACHE extends IInputRecipeCache> implements RecipeType<RECIPE>,
        AsariMekAddonRecipTypeProvider<RECIPE,INPUT_CACHE> {
    public static final AsariMekAddonRecipeTypeDefrredRegister RECIPE_TYPES = new AsariMekAddonRecipeTypeDefrredRegister(AsariMekAddon.MOD_ID);

    public static final AsariMekAddonRecipeTypeRegistryObject<AntiprotonicNucleosynthesizeRecipe,AsariMekAddonInputRecipeCache.SingleChemical<AntiprotonicNucleosynthesizeRecipe>> ANTIPROTONIC_NUCLEOSYNTHESIZE =
            register("antiprotonic_nucleosynthesize",recipe_Type -> new AsariMekAddonInputRecipeCache.SingleChemical<>(recipe_Type,AntiprotonicNucleosynthesizeRecipe::getInput));

    private static <RECIPE extends MekanismRecipe,INPUT_CACHE extends IInputRecipeCache> AsariMekAddonRecipeTypeRegistryObject<RECIPE,INPUT_CACHE> register(
            String name, Function<AsariMekAddonRecipeType<RECIPE,INPUT_CACHE>,INPUT_CACHE> inputCacheCreator) {
        return RECIPE_TYPES.registerRecipeType(name,() -> new AsariMekAddonRecipeType<>(name,inputCacheCreator));
    }

    public static void clearCache() {
        for (AsariMekAddonRecipTypeProvider<?, ?> recipeTypeProvider : RECIPE_TYPES.getAllRecipeType()) {
            recipeTypeProvider.getAMAType().clearCaches();
        }
    }

    private List<RECIPE> cachedRecipes = Collections.emptyList();
    private final ResourceLocation registryName;
    private final INPUT_CACHE inputCache;

    private AsariMekAddonRecipeType(String name,Function<AsariMekAddonRecipeType<RECIPE,INPUT_CACHE>,INPUT_CACHE> inputCacheCreator) {
        this.registryName = AsariMekAddon.rl(name);
        this.inputCache = inputCacheCreator.apply(this);
    }

    @Override
    public String toString() { return registryName.toString();}

    @Override
    public ResourceLocation getRegistryName() { return registryName;}

    @Override
    public AsariMekAddonRecipeType<RECIPE,INPUT_CACHE> getAMAType() {return this;}

    private void clearCaches() {
        cachedRecipes = Collections.emptyList();
        inputCache.clear();
    }

    @Override
    public INPUT_CACHE getInputCache() {
        return inputCache;
    }

    public static Optional<? extends Recipe<?>> byKey(Level level, ResourceLocation id) {
        //Only allow looking up complete recipes or special recipes as we only use this method for vanilla recipe types
        // and special recipes return that they are not complete
        return level.getRecipeManager().byKey(id)
                .filter(recipe -> recipe.isSpecial() || !recipe.isIncomplete());
    }
    public List<RECIPE> getRecipes(Level world) {
        if (world == null) {
            return Collections.emptyList();
        }

        return world.getRecipeManager()
                .getAllRecipesFor(this)
                .stream()
                .filter(recipe -> recipe.isSpecial() || !recipe.isIncomplete())
                .toList();
    }
}
