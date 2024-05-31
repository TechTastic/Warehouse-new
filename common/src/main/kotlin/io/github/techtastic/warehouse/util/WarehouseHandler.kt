package io.github.techtastic.warehouse.util

import io.github.techtastic.warehouse.block.entity.WarehouseControllerBE
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB

class WarehouseHandler {
    val warehouses = hashMapOf<ResourceKey<Level>, HashMap<BlockPos, AABB>>()

    fun isInWarehouseArea(level: ResourceKey<Level>, pos: BlockPos): BlockPos? {
        this.warehouses[level]?.forEach { (controller, aabb) ->
            if (aabb.contains(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()))
                return controller
        }

        return null
    }

    fun addOrUpdateWarehouse(controller: WarehouseControllerBE) {
        controller.level?.let {
            this.warehouses.computeIfAbsent(it.dimension()) {
                hashMapOf()
            }[controller.blockPos] = controller.warehouseAABB
        }
    }

    fun removeWarehouse(controller: WarehouseControllerBE) {
        controller.level?.let { level ->
            this.warehouses[level.dimension()]?.remove(controller.blockPos)
        }
    }
}