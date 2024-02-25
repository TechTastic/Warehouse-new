package io.github.techtastic.witcheryrewoven.screen

import dev.architectury.registry.menu.MenuRegistry
import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.MOD_ID
import io.github.techtastic.witcheryrewoven.screen.witches_oven.WitchesOvenMenu
import net.minecraft.core.Registry

object WRMenuTypes {
    private val MENU_TYPES = DeferredRegister.create(MOD_ID, Registry.MENU_REGISTRY)

    val WITCHES_OVEN = MENU_TYPES.register("witches_oven") {
        MenuRegistry.ofExtended(::WitchesOvenMenu)
    }

    fun register() {
        MENU_TYPES.register()
    }
}