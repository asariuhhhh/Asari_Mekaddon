package asari.mekaddon.clinet.jei;

import asari.api.recipes.AntiprotonicNucleosynthesizeRecipe;
import asari.mekaddon.AsariMekAddon;

import mekanism.client.jei.MekanismJEIRecipeType;
import net.minecraft.resources.ResourceLocation;


public class AsariMekAddonJEIRecipeType {

    public static final MekanismJEIRecipeType<AntiprotonicNucleosynthesizeRecipe> ANTIPROTONIC_NUCLEOSYNTHESIZE =
            new MekanismJEIRecipeType<>(rl("antiprotonic_nucleosynthesize"), AntiprotonicNucleosynthesizeRecipe.class);


    private static ResourceLocation rl(String path) {
        return new ResourceLocation(AsariMekAddon.MOD_ID, path);
    }


}
