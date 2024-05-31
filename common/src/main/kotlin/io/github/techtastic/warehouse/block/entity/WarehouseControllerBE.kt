package io.github.techtastic.warehouse.block.entity

import dev.architectury.networking.NetworkManager
import dev.architectury.registry.menu.ExtendedMenuProvider
import io.github.techtastic.warehouse.Warehouse.WAREHOUSE_HANDLER
import io.github.techtastic.warehouse.block.WarehouseBlockEntities
import io.github.techtastic.warehouse.network.WarehouseNetworking
import io.github.techtastic.warehouse.network.packet.AABBSyncS2CPacket
import io.github.techtastic.warehouse.screen.controller.WarehouseControllerMenu
import io.github.techtastic.warehouse.util.IWarehouseStorage
import io.netty.buffer.Unpooled
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Vec3i
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.world.phys.AABB

class WarehouseControllerBE(pos: BlockPos, state: BlockState):
        BlockEntity(WarehouseBlockEntities.WAREHOUSE_CONTROLLER.get(), pos, state), ExtendedMenuProvider {
    var warehouseAABB = AABB(worldPosition).inflate(2.0)
    val limitAABB = AABB(worldPosition).inflate(0.9)

    var containers = listOf<Container>()

    init {
        val directionVec = blockState.getValue(HORIZONTAL_FACING).opposite.normal.multiply(3)
        this.updateAABB(this.warehouseAABB.move(directionVec.x.toDouble(), 2.0, directionVec.z.toDouble()))
    }

    override fun saveAdditional(tag: CompoundTag) {
        saveAABB(tag)

        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)

        loadAABB(tag)
    }

    override fun setChanged() {
        super.setChanged()

        if (level == null || level!!.isClientSide)
            return

        val level = level as ServerLevel

        // Sync AABB
        val buf = FriendlyByteBuf(Unpooled.buffer())
        AABBSyncS2CPacket(this.warehouseAABB, this.worldPosition).toBytes(buf)
        NetworkManager.sendToPlayers(level.server.playerList.players, WarehouseNetworking.AABB_SYNC_PACKET_ID, buf)

        // Recheck Containers
        this.updateContainersList()
    }

    override fun createMenu(syncId: Int, inventory: Inventory, player: Player): AbstractContainerMenu =
            WarehouseControllerMenu(syncId, inventory, this)

    override fun getDisplayName(): Component = blockState.block.name

    override fun saveExtraData(buf: FriendlyByteBuf) {

        buf.writeBlockPos(worldPosition)
    }

    fun tick(level: Level, pos: BlockPos, state: BlockState, warehouse: WarehouseControllerBE) {
        WAREHOUSE_HANDLER.addOrUpdateWarehouse(warehouse)
    }

    // Container Manipulation

    private fun getAllContainers(level: Level, aabb: AABB): List<Container> {
        val list = mutableListOf<Container>()

        for (x in aabb.minX.toInt() until aabb.maxX.toInt()) {
            for (y in aabb.minY.toInt() until aabb.maxY.toInt()) {
                for (z in aabb.minZ.toInt() until aabb.maxZ.toInt()){
                    val be = level.getBlockEntity(BlockPos(x, y, z))
                    if (be is IWarehouseStorage)
                        list.add(be.getContainer())
                    else if (be is Container)
                        list.add(be)
                }
            }
        }

        return list
    }

    fun updateContainersList() {
        this.containers = this.level?.let { getAllContainers(it, this.warehouseAABB) } ?: listOf()
    }

    // AABB Manipulation and Save/Load

    fun updateAABB(aabb: AABB) {
        if (aabb.intersects(AABB(this.blockPos).deflate(0.1)) || !aabb.intersects(this.limitAABB))
            return
        this.warehouseAABB = aabb
        updateContainersList()
        WAREHOUSE_HANDLER.addOrUpdateWarehouse(this)
        this.setChanged()
    }

    fun getAABBForRendering(): AABB {
        return this.warehouseAABB.toRenderingAABB()
    }

    fun moveAABB(direction: Direction, increment: Int) {
        val directionVec = direction.normal.multiply(increment)
        this.updateAABB(this.warehouseAABB.move(directionVec.x.toDouble(), directionVec.y.toDouble(), directionVec.z.toDouble()))
    }

    private fun saveAABB(tag: CompoundTag) {
        tag.putIntArray("warehouse\$aabb", this.warehouseAABB.toIntArray())
    }

    private fun loadAABB(tag: CompoundTag) {
        val array = tag.getIntArray("warehouse\$aabb")
        this.warehouseAABB = array.toAABB()
    }

    private fun AABB.toRenderingAABB(): AABB {
        val offsetX = worldPosition.x.toDouble()
        val offsetY = worldPosition.y.toDouble()
        val offsetZ = worldPosition.z.toDouble()

        return move(-offsetX, -offsetY, -offsetZ)
    }

    private fun Int.toBoolean(): Boolean = this == 1

    private fun Boolean.toInt(): Int = if (this) 1 else 0

    companion object {
        fun AABB.toIntArray() = IntArray(6) {
            when(it) {
                0 -> minX.toInt()
                1 -> minY.toInt()
                2 -> minZ.toInt()
                3 -> maxX.toInt()
                4 -> maxY.toInt()
                else -> maxZ.toInt()
            }
        }

        fun IntArray.toAABB() = AABB(
            get(0).toDouble(),
            get(1).toDouble(),
            get(2).toDouble(),
            get(3).toDouble(),
            get(4).toDouble(),
            get(5).toDouble()
        )

        fun AABB.getMinCorner() = Vec3i(minX, minY, minZ)

        fun AABB.getMaxCorner() = Vec3i(maxX, maxY, maxZ)
    }
}