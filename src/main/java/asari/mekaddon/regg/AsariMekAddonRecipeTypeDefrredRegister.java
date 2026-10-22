package asari.mekaddon.regg;

import asari.mekaddon.recipe.AsariMekAddonRecipTypeProvider;
import asari.mekaddon.recipe.AsariMekAddonRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.registration.WrappedDeferredRegister;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.core.registries.Registries;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class AsariMekAddonRecipeTypeDefrredRegister extends WrappedDeferredRegister<RecipeType<?>> {

    private final List<AsariMekAddonRecipTypeProvider<?,?>> recipeTypes = new ArrayList<>();

    public AsariMekAddonRecipeTypeDefrredRegister(String modid) {
        super(modid, Registries.RECIPE_TYPE);
    }
    public <RECIPE extends MekanismRecipe,AMA_INPUT_CACHE extends IInputRecipeCache> AsariMekAddonRecipeTypeRegistryObject<RECIPE,AMA_INPUT_CACHE> registerRecipeType(
            String name, Supplier<? extends AsariMekAddonRecipeType<RECIPE,AMA_INPUT_CACHE>> sup) {
        AsariMekAddonRecipeTypeRegistryObject<RECIPE,AMA_INPUT_CACHE> registeredRecipeType = register(name,sup,AsariMekAddonRecipeTypeRegistryObject::new);
        recipeTypes.add(registeredRecipeType);
        return registeredRecipeType;
    }

    public List<AsariMekAddonRecipTypeProvider<?,?>> getAllRecipeType() {
        return Collections.unmodifiableList(recipeTypes);
    }

}
