package io.github.techtastic.warehouse.network.packet

import dev.architectury.networking.NetworkManager
import io.github.techtastic.warehouse.block.entity.WarehouseControllerBE
import io.github.techtastic.warehouse.block.entity.WarehouseControllerBE.Companion.toAABB
import io.github.techtastic.warehouse.block.entity.WarehouseControllerBE.Companion.toIntArray
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.phys.AABB
import java.util.function.Supplier

class AABBSyncS2CPacket(val aabb: AABB, private val pos: BlockPos) {
    constructor(buf: FriendlyByteBuf) :
            this(buf.readVarIntArray().toAABB(), buf.readBlockPos())

    constructor(buf: FriendlyByteBuf, context: NetworkManager.PacketContext) : this(buf) {
        this.apply(Supplier<NetworkManager.PacketContext> { context })
    }

    fun toBytes(buf: FriendlyByteBuf) {
        buf.writeVarIntArray(this.aabb.toIntArray())
        buf.writeBlockPos(pos)
    }

    fun apply(context: Supplier<NetworkManager.PacketContext>) {
        context.get().queue {
            val be = Minecraft.getInstance().level?.getBlockEntity(pos)
            if (be is WarehouseControllerBE)
                be.updateAABB(this.aabb)
        }
    }
}