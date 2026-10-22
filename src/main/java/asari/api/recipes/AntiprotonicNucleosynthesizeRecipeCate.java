package asari.api.recipes;

import asari.mekaddon.block.AAblocks;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.jei.BaseRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;

import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;

import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.SlotType;

import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import org.jetbrains.annotations.NotNull;

public class AntiprotonicNucleosynthesizeRecipeCate extends BaseRecipeCategory<AntiprotonicNucleosynthesizeRecipe> {

    private final GuiGauge<?> input;
    private final GuiGauge<?> output;

    public AntiprotonicNucleosynthesizeRecipeCate(IGuiHelper helper,MekanismJEIRecipeType<AntiprotonicNucleosynthesizeRecipe> recipeType) {
        super(helper,recipeType, AAblocks.ANTIPROTONIC_NUCLEOSYNTHESIZE,4,13,168,60);
        addSlot(SlotType.INPUT,5,56).with(SlotOverlay.MINUS);
        addSlot(SlotType.OUTPUT, 155, 56).with(SlotOverlay.PLUS);
        input = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 25, 13));
        output = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 133, 13));
        addConstantProgress(ProgressType.LARGE_RIGHT, 64, 39);
    }
    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder,AntiprotonicNucleosynthesizeRecipe recipe,@NotNull IFocusGroup focusGroup) {
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, input, recipe.getInput().getRepresentations());
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }
}
