package asari.mekaddon.clinet.menu;

import asari.mekaddon.clinet.jei.AsariMekAddonJEIRecipeType;
import asari.mekaddon.mchines.AntiprotonicNucleosynthesize;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Inventory;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class AntiprotonicNucleosynthesizeGui extends GuiConfigurableTile<AntiprotonicNucleosynthesize, MekanismTileContainer<AntiprotonicNucleosynthesize>> {

    public AntiprotonicNucleosynthesizeGui(MekanismTileContainer<AntiprotonicNucleosynthesize> container, Inventory inv, Component title) {
        super(container,inv,title);
        inventoryLabelY += 2;
        titleLabelY = 4;
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiGasGauge(() -> tile.inputTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 25, 13))
                .warning(WarningTracker.WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT));
        addRenderableWidget(new GuiGasGauge(() -> tile.outputTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 133, 13))
                .warning(WarningTracker.WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.LARGE_RIGHT, this, 64, 39)).jeiCategories(AsariMekAddonJEIRecipeType.ANTIPROTONIC_NUCLEOSYNTHESIZE)
                .warning(WarningTracker.WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        drawString(guiGraphics, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
