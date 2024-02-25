package io.github.techtastic.witcheryrewoven.fabric.datagen

import io.github.techtastic.witcheryrewoven.datagen.WRCommonDatagen
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

class WRRecipeGenerator(gen: FabricDataGenerator) : FabricRecipeProvider(gen) {
    override fun generateRecipes(recipes: Consumer<FinishedRecipe>) {
        WRCommonDatagen.generateRecipes(recipes)
    }
}