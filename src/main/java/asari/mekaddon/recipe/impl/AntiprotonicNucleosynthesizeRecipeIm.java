package asari.mekaddon.recipe.impl;

import asari.api.recipes.AntiprotonicNucleosynthesizeRecipe;
import asari.mekaddon.block.AAblocks;
import asari.mekaddon.recipe.AsariMekAddonRecipeType;
import asari.mekaddon.recipe.serializer.AntiprotonicNucleosynthesizeSerializerRecipe;
import asari.mekaddon.reg.AsariMekAddonSeri;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class AntiprotonicNucleosynthesizeRecipeIm extends AntiprotonicNucleosynthesizeRecipe {

    public AntiprotonicNucleosynthesizeRecipeIm(ResourceLocation id, ChemicalStackIngredient.GasStackIngredient input, GasStack output) {
        super(id, input, output);
    }
    @Override
    public RecipeType<AntiprotonicNucleosynthesizeRecipe> getType() {
        return AsariMekAddonRecipeType.ANTIPROTONIC_NUCLEOSYNTHESIZE.get();
    }

    @Override
    public RecipeSerializer<AntiprotonicNucleosynthesizeRecipe> getSerializer() {
        return AsariMekAddonSeri.ANTIPROTONIC_NUCLEOSYNTHESIZE.get();
    }

    @Override
    public String getGroup() {
        return AAblocks.ANTIPROTONIC_NUCLEOSYNTHESIZE.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return AAblocks.ANTIPROTONIC_NUCLEOSYNTHESIZE.getItemStack();
    }
}
