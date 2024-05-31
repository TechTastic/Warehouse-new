package io.github.techtastic.warehouse.network

import dev.architectury.networking.NetworkChannel
import dev.architectury.networking.NetworkManager
import io.github.techtastic.warehouse.Warehouse.MOD_ID
import io.github.techtastic.warehouse.network.packet.AABBSyncS2CPacket
import net.minecraft.resources.ResourceLocation

object WarehouseNetworking {
    val CHANNEL = NetworkChannel.create(ResourceLocation(MOD_ID, "networking_channel"))

    val AABB_SYNC_PACKET_ID = ResourceLocation(MOD_ID, "aabb_sync_s2c_packet")

    fun register() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, AABB_SYNC_PACKET_ID, ::AABBSyncS2CPacket)
        CHANNEL.register(
                AABBSyncS2CPacket::class.java,
                AABBSyncS2CPacket::toBytes,
                ::AABBSyncS2CPacket,
                AABBSyncS2CPacket::apply
        )
    }
}