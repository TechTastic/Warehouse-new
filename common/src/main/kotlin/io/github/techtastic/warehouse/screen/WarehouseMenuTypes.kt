package io.github.techtastic.warehouse.screen

import dev.architectury.registry.menu.MenuRegistry
import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.warehouse.Warehouse.MOD_ID
import io.github.techtastic.warehouse.screen.controller.WarehouseControllerMenu
import net.minecraft.core.Registry

object WarehouseMenuTypes {
    private val MENU_TYPES = DeferredRegister.create(MOD_ID, Registry.MENU_REGISTRY)

    val WAREHOUSE_CONTROLLER_MENU_TYPE = MENU_TYPES.register("warehouse") { MenuRegistry.ofExtended(::WarehouseControllerMenu) }

    fun register() {
        MENU_TYPES.register()
    }
}