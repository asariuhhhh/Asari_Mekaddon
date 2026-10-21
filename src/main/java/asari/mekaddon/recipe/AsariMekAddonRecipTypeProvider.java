package asari.mekaddon.recipe;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface AsariMekAddonRecipTypeProvider<RECIPE extends MekanismRecipe,INPUT_CACHE extends IInputRecipeCache> {
    default ResourceLocation getRegistryName() {
        return getAMAType().getRegistryName();
    }
    AsariMekAddonRecipeType<RECIPE,INPUT_CACHE> getAMAType();
    default INPUT_CACHE getInputCache() {
        return getAMAType().getInputCache();
    }
    @NotNull
    default List<RECIPE> getRecipe(@Nullable Level world) {
        return getAMAType().getRecipes(world);
    }
    default Stream<RECIPE> stream(@Nullable Level world) {
        return getRecipe(world).stream();
    }
    @Nullable
    default RECIPE findFirst(@Nullable Level world, Predicate<RECIPE> matchCriteria) {
        return stream(world).filter(matchCriteria).findFirst().orElse(null);
    }
    default boolean contains(@Nullable Level world,Predicate<RECIPE> matchCriteria) {
        return stream(world).anyMatch(matchCriteria);
    }
}
