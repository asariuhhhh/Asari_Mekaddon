package asari.mekaddon.recipe.serializer;

import asari.api.recipes.AntiprotonicNucleosynthesizeRecipe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.Mekanism;
import mekanism.common.recipe.serializer.GasToGasRecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class AntiprotonicNucleosynthesizeSerializerRecipe<RECIPE extends AntiprotonicNucleosynthesizeRecipe> implements RecipeSerializer<RECIPE> {
    private final AntiprotonicNucleosynthesizeSerializerRecipe.IFactory<RECIPE> factory;

    public AntiprotonicNucleosynthesizeSerializerRecipe(IFactory<RECIPE> factory) {
        this.factory = factory;
    }

    @NotNull
    @Override
    public RECIPE fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
        JsonElement input = GsonHelper.isArrayNode(json, JsonConstants.INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.INPUT);
        ChemicalStackIngredient.GasStackIngredient inputIngredient = IngredientCreatorAccess.gas().deserialize(input);
        GasStack output = SerializerHelper.getGasStack(json, JsonConstants.OUTPUT);
        if (output.isEmpty()) {
            throw new JsonSyntaxException("Recipe output must not be empty.");
        }
        return this.factory.create(recipeId, inputIngredient, output);
    }

    @Override
    public RECIPE fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        try {
            ChemicalStackIngredient.GasStackIngredient inputIngredient = IngredientCreatorAccess.gas().read(buffer);
            GasStack output = GasStack.readFromPacket(buffer);
            return this.factory.create(recipeId, inputIngredient, output);
        } catch (Exception e) {
            Mekanism.logger.error("Error reading gas to gas recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RECIPE recipe) {
        try {
            recipe.write(buffer);
        } catch (Exception e) {
            Mekanism.logger.error("Error writing gas to gas recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends AntiprotonicNucleosynthesizeRecipe> {

        RECIPE create(ResourceLocation id, ChemicalStackIngredient.GasStackIngredient input, GasStack output);
    }
}
