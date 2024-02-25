package io.github.techtastic.witcheryrewoven.recipe

import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.MOD_ID
import io.github.techtastic.witcheryrewoven.recipe.custom.WitchesOvenRecipe
import net.minecraft.core.Registry
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType

object WRRecipes {
    private val SERIALIZERS: DeferredRegister<RecipeSerializer<*>> = DeferredRegister.create(MOD_ID, Registry.RECIPE_SERIALIZER_REGISTRY)
    private val TYPES: DeferredRegister<RecipeType<*>> = DeferredRegister.create(MOD_ID, Registry.RECIPE_TYPE_REGISTRY)

    val WITCHES_OVEN_SERIALIZER = SERIALIZERS.register("oven_fumigation") { WitchesOvenRecipe.Serializer.INSTANCE }
    val WITCHES_IRON_OVEN_TYPE = TYPES.register("oven_fumigation") { WitchesOvenRecipe.Type.INSTANCE }

    fun register() {
        SERIALIZERS.register()
        TYPES.register()
    }
}