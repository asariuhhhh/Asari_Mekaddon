package asari.mekaddon.clinet.jei;

import asari.api.recipes.AntiprotonicNucleosynthesizeRecipe;
import asari.api.recipes.AntiprotonicNucleosynthesizeRecipeCate;
import asari.mekaddon.block.AAblocks;
import asari.mekaddon.block.AAblocktypes;
import asari.mekaddon.recipe.AsariMekAddonRecipTypeProvider;
import asari.mekaddon.recipe.AsariMekAddonRecipeType;
import mekanism.client.jei.machine.GasToGasRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mekanism.client.jei.CatalystRegistryHelper;
import mekanism.client.jei.MekanismJEIRecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Minecart;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@JeiPlugin
public class AsariMekAddonJEI implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation("asari_mekaddon", "jei_plugin");
    }


    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new AntiprotonicNucleosynthesizeRecipeCate(guiHelper,AsariMekAddonJEIRecipeType.ANTIPROTONIC_NUCLEOSYNTHESIZE));


    }
    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, MekanismJEIRecipeType.OXIDIZING, AAblocks.TEST_G);
        CatalystRegistryHelper.register(registry,AsariMekAddonJEIRecipeType.ANTIPROTONIC_NUCLEOSYNTHESIZE,AAblocks.ANTIPROTONIC_NUCLEOSYNTHESIZE);
    }
    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        AsariMekAddonRecipeRegistryHelper.register(registry,AsariMekAddonJEIRecipeType.ANTIPROTONIC_NUCLEOSYNTHESIZE,AsariMekAddonRecipeType.ANTIPROTONIC_NUCLEOSYNTHESIZE);

    }
}