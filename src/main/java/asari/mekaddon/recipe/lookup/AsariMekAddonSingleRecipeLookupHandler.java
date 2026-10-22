package asari.mekaddon.recipe.lookup;

import asari.mekaddon.recipe.lookup.cache.AsariMekAddonInputRecipeCache;
import asari.mekaddon.recipe.lookup.cache.AsariMekAddonSingleInputRecipeCache;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface AsariMekAddonSingleRecipeLookupHandler<INPUT,RECIPE extends MekanismRecipe & Predicate<INPUT>,INPUT_CACHE extends AsariMekAddonSingleInputRecipeCache<INPUT,?,RECIPE,?>>
          extends AsariMekAddonRecipeLookupHandler.AsariMekAddonRecipeTypedLookupHandler<RECIPE, INPUT_CACHE> {
        /**
         * Checks if there is a matching recipe of type {@link #getAMAType()} that has the given input.
         *
         * @param input Recipe input.
         *
         * @return {@code true} if there is a match, {@code false} if there isn't.
         */
        default boolean containsRecipe(INPUT input) {
            return getAMAType().getInputCache().containsInput(getHandlerWorld(), input);
        }

        /**
         * Finds the first recipe for the type of recipe we handle ({@link #getAMAType()}) by looking up the given input against the recipe type's input cache.
         *
         * @param input Recipe input.
         *
         * @return Recipe matching the given input, or {@code null} if no recipe matches.
         */
        @Nullable
        default RECIPE findFirstRecipe(INPUT input) {
            return getAMAType().getInputCache().findFirstRecipe(getHandlerWorld(), input);
        }

        /**
         * Finds the first recipe for the type of recipe we handle ({@link #getAMAType()}) by looking up the given input against the recipe type's input cache.
         *
         * @param inputHandler Input handler to grab the recipe input from.
         *
         * @return Recipe matching the given input, or {@code null} if no recipe matches.
         */
        @Nullable
        default RECIPE findFirstRecipe(IInputHandler<INPUT> inputHandler) {
            return findFirstRecipe(inputHandler.getInput());
        }

        /**
         * Helper interface to make the generics that we have to pass to {@link ISingleRecipeLookupHandler} not as messy.
         */
        interface ItemRecipeLookupHandler<RECIPE extends MekanismRecipe & Predicate<ItemStack>> extends ISingleRecipeLookupHandler<ItemStack, RECIPE, InputRecipeCache.SingleItem<RECIPE>> {
        }

        /**
         * Helper interface to make the generics that we have to pass to {@link ISingleRecipeLookupHandler} not as messy.
         */
        interface FluidRecipeLookupHandler<RECIPE extends MekanismRecipe & Predicate<FluidStack>> extends ISingleRecipeLookupHandler<FluidStack, RECIPE, InputRecipeCache.SingleFluid<RECIPE>> {
        }

        /**
         * Helper interface to make the generics that we have to pass to {@link ISingleRecipeLookupHandler} not as messy.
         */
        interface ChemicalRecipeLookupHandler<RECIPE extends MekanismRecipe & Predicate<GasStack>>
                extends AsariMekAddonSingleRecipeLookupHandler<GasStack, RECIPE, AsariMekAddonInputRecipeCache.SingleChemical<RECIPE>> {

            /**
             * Helper wrapper to convert a chemical to a chemical stack and pass it to {@link #containsRecipe(Object)} to make validity predicates easier and cleaner.
             */
            default boolean containsRecipe(mekanism.api.chemical.Chemical input) {
                return containsRecipe(new GasStack((mekanism.api.chemical.gas.Gas) input,1));
            }
        }
    }

