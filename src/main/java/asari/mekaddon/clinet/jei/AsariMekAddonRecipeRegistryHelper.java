package asari.mekaddon.clinet.jei;

import asari.mekaddon.recipe.AsariMekAddonRecipTypeProvider;
import mekanism.api.providers.IItemProvider;
import mekanism.api.recipes.ItemStackToFluidRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.RotaryRecipe;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.impl.NutritionalLiquifierIRecipe;
import mekanism.common.registries.MekanismFluids;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AsariMekAddonRecipeRegistryHelper {

    public static <RECIPE extends MekanismRecipe> void register(IRecipeRegistration registry, MekanismJEIRecipeType<RECIPE> recipeType,
                                                                AsariMekAddonRecipTypeProvider<RECIPE, ?> type) {
        ClientLevel world = Minecraft.getInstance().level;
        if (world != null) {
            List<RECIPE> recipes = type.getAMAType().getRecipes(world);
            if (!recipes.isEmpty()) {
                register(registry, recipeType, recipes);
            }
        }
    }

    public static <RECIPE> void register(IRecipeRegistration registry, MekanismJEIRecipeType<RECIPE> recipeType, List<RECIPE> recipes) {
        if (!recipes.isEmpty()) {
            registry.addRecipes(MekanismJEI.recipeType(recipeType), recipes);
        }
    }




}
