package io.github.techtastic.warehouse.block

import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.warehouse.Warehouse.MOD_ID
import io.github.techtastic.warehouse.block.entity.WarehouseControllerBE
import net.minecraft.core.Registry
import net.minecraft.world.level.block.entity.BlockEntityType

object WarehouseBlockEntities {
    private val BLOCK_ENTITIES = DeferredRegister.create(MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY)

    val WAREHOUSE_CONTROLLER = BLOCK_ENTITIES.register("warehouse_controller") {
        BlockEntityType.Builder.of(::WarehouseControllerBE, WarehouseBlocks.WAREHOUSE_CONTROLLER.get()).build(null)
    }

    fun register() {
        BLOCK_ENTITIES.register()
    }
}