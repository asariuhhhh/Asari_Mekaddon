package asari.api.recipes;

import mekanism.api.annotations.ParametersAreNotNullByDefault;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.chemical.ChemicalToChemicalRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.resources.ResourceLocation;

@ParametersAreNotNullByDefault
public abstract class AntiprotonicNucleosynthesizeRecipe extends ChemicalToChemicalRecipe<Gas, GasStack, ChemicalStackIngredient.GasStackIngredient> {

    public AntiprotonicNucleosynthesizeRecipe(ResourceLocation id, ChemicalStackIngredient.GasStackIngredient input,GasStack output) {
        super(id, input, output);
    }
}
