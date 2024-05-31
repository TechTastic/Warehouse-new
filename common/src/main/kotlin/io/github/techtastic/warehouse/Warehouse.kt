package io.github.techtastic.warehouse

import dev.architectury.event.events.common.BlockEvent
import dev.architectury.event.events.common.ChunkEvent
import dev.architectury.event.events.common.EntityEvent
import dev.architectury.event.events.common.InteractionEvent
import dev.architectury.event.events.common.LootEvent
import dev.architectury.event.events.common.PlayerEvent
import dev.architectury.event.events.common.TickEvent
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry
import dev.architectury.registry.menu.MenuRegistry
import io.github.techtastic.warehouse.block.WarehouseBlockEntities
import io.github.techtastic.warehouse.block.WarehouseBlocks
import io.github.techtastic.warehouse.block.entity.renderer.WarehouseControllerBER
import io.github.techtastic.warehouse.network.WarehouseNetworking
import io.github.techtastic.warehouse.screen.WarehouseMenuTypes
import io.github.techtastic.warehouse.screen.controller.WarehouseControllerScreen
import io.github.techtastic.warehouse.util.WarehouseEventHandler
import io.github.techtastic.warehouse.util.WarehouseHandler

object Warehouse {
    const val MOD_ID = "warehouse"
    val WAREHOUSE_HANDLER = WarehouseHandler()

    fun init() {
        WarehouseBlocks.register()
        WarehouseBlockEntities.register()

        WarehouseNetworking.register()

        WarehouseMenuTypes.register()

        BlockEvent.PLACE.register(WarehouseEventHandler::onPlace)
        BlockEvent.BREAK.register(WarehouseEventHandler::onBreak)
        ChunkEvent.LOAD_DATA.register(WarehouseEventHandler::onLoad)
    }

    fun initClient() {
        BlockEntityRendererRegistry.register(WarehouseBlockEntities.WAREHOUSE_CONTROLLER.get(), ::WarehouseControllerBER)

        MenuRegistry.registerScreenFactory(WarehouseMenuTypes.WAREHOUSE_CONTROLLER_MENU_TYPE.get(), ::WarehouseControllerScreen)
    }
}