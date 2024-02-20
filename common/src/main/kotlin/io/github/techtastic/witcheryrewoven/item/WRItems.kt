package io.github.techtastic.witcheryrewoven.item

import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.MOD_ID
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

object WRItems {
    private val ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY)

    // Creative Tab

    private val LOGO = ITEMS.register("logo") { Item(Item.Properties()) }
    val TAB = CreativeTabRegistry.create(ResourceLocation(MOD_ID, "tab")) {
        ItemStack(LOGO.get())
    }

    // Jar Items

    val SOFT_CLAY_JAR = ITEMS.register("soft_clay_jar") { Item(Item.Properties().tab(TAB)) }
    val CLAY_JAR = ITEMS.register("clay_jar") { Item(Item.Properties().tab(TAB)) }
    val EXHALE_OF_THE_HORNED_ONE = ITEMS.register("exhale_of_the_horned_one") { Item(Item.Properties().tab(TAB)) }
    val HINT_OF_REBIRTH = ITEMS.register("hint_of_rebirth") { Item(Item.Properties().tab(TAB)) }
    val BREATH_OF_THE_GODDESS = ITEMS.register("breath_of_the_goddess") { Item(Item.Properties().tab(TAB)) }
    val FOUL_FUME = ITEMS.register("foul_fume") { Item(Item.Properties().tab(TAB)) }
    val WHIFF_OF_MAGIC = ITEMS.register("whiff_of_magic") { Item(Item.Properties().tab(TAB)) }
    val REEK_OF_MISFORTUNE = ITEMS.register("reek_of_misfortune") { Item(Item.Properties().tab(TAB)) }
    val ODOR_OF_PURITY = ITEMS.register("odor_of_purity") { Item(Item.Properties().tab(TAB)) }

    // MISC

    val WOOD_ASH = ITEMS.register("wood_ash") { Item(Item.Properties().tab(TAB)) }

    fun register() {
        ITEMS.register()
    }
}