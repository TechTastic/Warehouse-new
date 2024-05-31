package io.github.techtastic.warehouse.screen.controller

import io.github.techtastic.warehouse.block.entity.WarehouseControllerBE
import io.github.techtastic.warehouse.screen.WarehouseMenuTypes
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.*
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike

class WarehouseControllerMenu(syncId: Int, val inventory: Inventory, val warehouse: WarehouseControllerBE) :
        AbstractContainerMenu(WarehouseMenuTypes.WAREHOUSE_CONTROLLER_MENU_TYPE.get(), syncId) {

    constructor(syncId: Int, inventory: Inventory, extraData: FriendlyByteBuf):
            this(syncId, inventory, inventory.player.level.getBlockEntity(extraData.readBlockPos()) as WarehouseControllerBE)

    init {
        checkContainerSize(this.inventory, 0)
    }

    fun getContainers() = this.warehouse.containers

    fun getAllItems(): List<ItemStack> {
        val list = mutableListOf<ItemStack>()
        this.getContainers().forEach {
            for (slot in 0 until it.containerSize) {
                if (!it.getItem(slot).isEmpty)
                    list.add(it.getItem(slot))
            }
        }

        return list
    }

    override fun stillValid(player: Player): Boolean =
            this.inventory.stillValid(player)

    override fun clickMenuButton(player: Player, id: Int): Boolean {
        if (player.level.isClientSide)
            return super.clickMenuButton(player, id)

        when (id) {
            0 -> this.warehouse.moveAABB(Direction.SOUTH, 1)
            1 -> this.warehouse.moveAABB(Direction.NORTH, 1)
            2 -> this.warehouse.moveAABB(Direction.UP, 1)
            3 -> this.warehouse.moveAABB(Direction.DOWN, 1)
            4 -> this.warehouse.moveAABB(Direction.EAST, 1)
            5 -> this.warehouse.moveAABB(Direction.WEST, 1)
            else -> return super.clickMenuButton(player, id)
        }

        return true
    }
}