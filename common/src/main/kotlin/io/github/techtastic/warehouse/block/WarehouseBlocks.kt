package io.github.techtastic.warehouse.block

import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.warehouse.Warehouse
import io.github.techtastic.warehouse.block.custom.WarehouseControllerBlock
import net.minecraft.core.Registry
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour

object WarehouseBlocks {
    private val BLOCKS = DeferredRegister.create(Warehouse.MOD_ID, Registry.BLOCK_REGISTRY)
    private val BLOCK_ITEMS = DeferredRegister.create(Warehouse.MOD_ID, Registry.ITEM_REGISTRY)

    val WAREHOUSE_CONTROLLER = BLOCKS.register("warehouse_controller") {
        WarehouseControllerBlock(BlockBehaviour.Properties.copy(Blocks.CHEST))
    }

    fun register() {
        BLOCKS.register()

        BLOCKS.forEach {
            BLOCK_ITEMS.register(it.id) {
                BlockItem(it.get(), Item.Properties())
            }
        }

        BLOCK_ITEMS.register()
    }
}