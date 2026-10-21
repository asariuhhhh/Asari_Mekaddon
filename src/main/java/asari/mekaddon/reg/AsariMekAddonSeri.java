package asari.mekaddon.reg;

import asari.api.recipes.AntiprotonicNucleosynthesizeRecipe;
import asari.mekaddon.AsariMekAddon;
import asari.mekaddon.recipe.impl.AntiprotonicNucleosynthesizeRecipeIm;
import asari.mekaddon.recipe.serializer.AntiprotonicNucleosynthesizeSerializerRecipe;
import mekanism.common.registration.impl.RecipeSerializerDeferredRegister;
import mekanism.common.registration.impl.RecipeSerializerRegistryObject;

public class AsariMekAddonSeri {
    public static final RecipeSerializerDeferredRegister RECIPE_SERIALIZERS = new RecipeSerializerDeferredRegister(AsariMekAddon.MOD_ID);

    public static final RecipeSerializerRegistryObject<AntiprotonicNucleosynthesizeRecipe> ANTIPROTONIC_NUCLEOSYNTHESIZE = RECIPE_SERIALIZERS.register("antiprotonicnucleosynthesize",() -> new AntiprotonicNucleosynthesizeSerializerRecipe<>(AntiprotonicNucleosynthesizeRecipeIm::new));
}
