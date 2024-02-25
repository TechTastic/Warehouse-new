package io.github.techtastic.witcheryrewoven.datagen

import com.google.gson.JsonObject
import io.github.techtastic.witcheryrewoven.recipe.custom.WitchesOvenRecipe
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.core.Registry
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.ItemLike
import java.util.function.Consumer

class WitchesOvenRecipeBuilder(private val output: ItemLike, private val jar: ItemLike, private val input: Ingredient): RecipeBuilder {
    override fun unlockedBy(string: String, criterionTriggerInstance: CriterionTriggerInstance): RecipeBuilder = this

    override fun group(string: String?): RecipeBuilder = this

    override fun getResult(): Item = this.output.asItem()

    override fun save(consumer: Consumer<FinishedRecipe>, id: ResourceLocation) {
        consumer.accept(Result(id, output, jar, input))
    }

    class Result(private val id: ResourceLocation, private val output: ItemLike, private val jar: ItemLike, private val input: Ingredient): FinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            json.add("input", this.input.toJson())
            json.addProperty("jar", Registry.ITEM.getKey(this.jar.asItem()).toString())
            json.addProperty("output", Registry.ITEM.getKey(this.output.asItem()).toString())
        }

        override fun getId(): ResourceLocation = this.id

        override fun getType(): RecipeSerializer<*> = WitchesOvenRecipe.Serializer.INSTANCE

        override fun serializeAdvancement(): JsonObject? = null

        override fun getAdvancementId(): ResourceLocation? = null
    }
}