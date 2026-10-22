package asari.mekaddon.regg;

import asari.mekaddon.recipe.AsariMekAddonRecipTypeProvider;
import asari.mekaddon.recipe.AsariMekAddonRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.registration.WrappedRegistryObject;
import net.minecraftforge.registries.RegistryObject;

public class AsariMekAddonRecipeTypeRegistryObject<RECIPE extends MekanismRecipe,AMA_INPUT_CACHE extends IInputRecipeCache> extends
        WrappedRegistryObject<AsariMekAddonRecipeType<RECIPE,AMA_INPUT_CACHE>> implements AsariMekAddonRecipTypeProvider<RECIPE,AMA_INPUT_CACHE> {

    public AsariMekAddonRecipeTypeRegistryObject(RegistryObject<AsariMekAddonRecipeType<RECIPE,AMA_INPUT_CACHE>> registryObject) {
        super(registryObject);
    }
    @Override
    public  AsariMekAddonRecipeType<RECIPE,AMA_INPUT_CACHE> getAMAType() {
        return get();
    }
}
