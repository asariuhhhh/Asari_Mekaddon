package asari.mekaddon.clinet.jei;

import asari.api.recipes.AntiprotonicNucleosynthesizeRecipe;
import asari.mekaddon.AsariMekAddon;
import asari.mekaddon.recipe.AsariMekAddonRecipeType;
import mekanism.client.jei.MekanismJEIRecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static asari.mekaddon.AsariMekAddon.rl;

public class AsariMekAddonJEIRecipeType {

    public static final MekanismJEIRecipeType<AntiprotonicNucleosynthesizeRecipe> ANTIPROTONIC_NUCLEOSYNTHESIZE =
            new MekanismJEIRecipeType<>(rl("antiprotonic_nucleosynthesize"), AntiprotonicNucleosynthesizeRecipe.class);


    private static ResourceLocation rl(String path) {
        return new ResourceLocation(AsariMekAddon.MOD_ID, path);
    }


}
