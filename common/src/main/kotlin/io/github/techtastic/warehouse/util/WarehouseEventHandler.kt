package io.github.techtastic.warehouse.util

import dev.architectury.event.EventResult
import dev.architectury.utils.value.IntValue
import io.github.techtastic.warehouse.Warehouse.WAREHOUSE_HANDLER
import io.github.techtastic.warehouse.block.entity.WarehouseControllerBE
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.chunk.ChunkAccess

object WarehouseEventHandler {
    fun onPlace(level: Level, pos: BlockPos, state: BlockState, placer: Entity?): EventResult {
        getAndUpdateControllerForArea(level, pos)
        return EventResult.pass()
    }

    fun onBreak(level: Level, pos: BlockPos, state: BlockState, player: ServerPlayer, xp: IntValue?): EventResult {
        getAndUpdateControllerForArea(level, pos)
        return EventResult.pass()
    }

    fun onLoad(chunk: ChunkAccess, level: ServerLevel?, tag: CompoundTag): EventResult {
        if (level == null)
            return EventResult.pass()

        chunk.blockEntitiesPos.forEach {
            val controller = WAREHOUSE_HANDLER.isInWarehouseArea(level.dimension(), it) ?: return@forEach
            level.getBlockEntity(controller)?.setChanged()
        }

        return EventResult.pass()
    }

    private fun getAndUpdateControllerForArea(level: Level, pos: BlockPos) {
        val be = level.getBlockEntity(pos)
        if (be is Container || be is IWarehouseStorage) {
            val controllerPos = WAREHOUSE_HANDLER.isInWarehouseArea(level.dimension(), pos) ?: return
            (level.getBlockEntity(controllerPos) as WarehouseControllerBE).setChanged()
        }
    }
}