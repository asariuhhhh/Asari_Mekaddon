package asari.mekaddon.clinet.jei;

import asari.mekaddon.recipe.AsariMekAddonRecipTypeProvider;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import java.util.List;

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
