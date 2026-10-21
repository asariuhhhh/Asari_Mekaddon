package asari.mekaddon.clinet.menu;

import asari.mekaddon.mchines.test_g;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;


public class AAtest_g_menu extends GuiConfigurableTile<test_g, MekanismTileContainer<test_g>> {

    public AAtest_g_menu(MekanismTileContainer<test_g>container, Inventory inv, Component title) {
        super(container,inv,title);
        dynamicSlots = true;
    }
    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiHorizontalPowerBar(this,tile.getEnergyContainer(),115,75))
                .warning(WarningTracker.WarningType.NOT_ENOUGH_ENERGY,tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY));
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getActive));
        addRenderableWidget(new GuiGasGauge(() -> tile.gasTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 131, 13))
                .warning(WarningTracker.WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        addRenderableWidget(new GuiProgress(tile::getScaledProgress, ProgressType.LARGE_RIGHT, this, 64, 40)).jeiCategories(MekanismJEIRecipeType.OXIDIZING)
                .warning(WarningTracker.WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        drawString(guiGraphics, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
