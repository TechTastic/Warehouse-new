package io.github.techtastic.witcheryrewoven.recipe.custom

import com.google.gson.JsonObject
import io.github.techtastic.witcheryrewoven.block.entity.WitchesOvenBE
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class WitchesOvenRecipe(private val id: ResourceLocation, private val output: ItemStack, private val jarOutput: ItemStack, private val input: Ingredient):
        Recipe<WitchesOvenBE> {
    override fun matches(container: WitchesOvenBE, level: Level): Boolean =
            this.input.test(container.getItem(0))

    override fun assemble(container: WitchesOvenBE): ItemStack =
            this.output

    override fun canCraftInDimensions(i: Int, j: Int): Boolean =
            true

    override fun getResultItem(): ItemStack =
            this.output.copy()

    fun getJarItem(): ItemStack =
            this.jarOutput.copy()

    override fun getId(): ResourceLocation =
            this.id

    override fun getIngredients(): NonNullList<Ingredient?> {
        return NonNullList.of(Ingredient.EMPTY, this.input)
    }

    override fun getSerializer(): RecipeSerializer<*> =
            Serializer.INSTANCE

    override fun getType(): RecipeType<*> =
            Type.INSTANCE

    class Type: RecipeType<WitchesOvenRecipe> {
        companion object {
            @JvmStatic
            val INSTANCE = Type()
        }
    }

    class Serializer: RecipeSerializer<WitchesOvenRecipe> {
        companion object {
            @JvmStatic
            val INSTANCE = Serializer()
        }

        override fun fromJson(id: ResourceLocation, json: JsonObject): WitchesOvenRecipe =
                WitchesOvenRecipe(id, ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output")),
                        ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "jar")),
                        Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input")))

        override fun fromNetwork(id: ResourceLocation, buf: FriendlyByteBuf): WitchesOvenRecipe =
                WitchesOvenRecipe(id, buf.readItem(), buf.readItem(), Ingredient.fromNetwork(buf))

        override fun toNetwork(buf: FriendlyByteBuf, recipe: WitchesOvenRecipe) {
            buf.writeItem(recipe.resultItem)
            buf.writeItem(recipe.getJarItem())
            recipe.ingredients.get(0).toNetwork(buf)
        }
    }
}