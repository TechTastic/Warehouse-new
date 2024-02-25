package io.github.techtastic.witcheryrewoven.datagen

import io.github.techtastic.witcheryrewoven.item.WRItems
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import java.util.function.Consumer

object WRCommonDatagen {
    fun generateRecipes(recipes: Consumer<FinishedRecipe>) {
        WitchesOvenRecipeBuilder(WRItems.WOOD_ASH.get(), WRItems.FOUL_FUME.get(), Ingredient.of(Items.DARK_OAK_SAPLING, Items.ACACIA_SAPLING, Items.JUNGLE_SAPLING)).save(recipes)
    }
}