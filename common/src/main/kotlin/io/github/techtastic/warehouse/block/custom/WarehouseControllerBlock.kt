package io.github.techtastic.warehouse.block.custom

import dev.architectury.registry.menu.ExtendedMenuProvider
import dev.architectury.registry.menu.MenuRegistry
import io.github.techtastic.warehouse.Warehouse.WAREHOUSE_HANDLER
import io.github.techtastic.warehouse.block.entity.WarehouseControllerBE
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.world.phys.BlockHitResult

class WarehouseControllerBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING)

        super.createBlockStateDefinition(builder)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return super.getStateForPlacement(context)?.setValue(HORIZONTAL_FACING, context.horizontalDirection.opposite)
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
            WarehouseControllerBE(pos, state)

    @Deprecated("Deprecated in Java")
    override fun getRenderShape(state: BlockState): RenderShape =
            RenderShape.MODEL

    @Deprecated("Deprecated in Java")
    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult): InteractionResult {
        return if (level.isClientSide) {
            InteractionResult.SUCCESS
        } else {
            val menuProvider = getMenuProvider(state, level, pos)
            if (menuProvider != null)
                MenuRegistry.openExtendedMenu(player as ServerPlayer, level.getBlockEntity(pos) as WarehouseControllerBE as ExtendedMenuProvider)

            InteractionResult.CONSUME
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRemove(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        blockState2: BlockState,
        bl: Boolean
    ) {
        WAREHOUSE_HANDLER.removeWarehouse(level.getBlockEntity(blockPos) as WarehouseControllerBE)

        super.onRemove(blockState, level, blockPos, blockState2, bl)
    }

    override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T> =
            BlockEntityTicker { level: Level, pos : BlockPos, state : BlockState, blockEntity ->
                if (level.isClientSide) return@BlockEntityTicker

                if (blockEntity is WarehouseControllerBE)
                    blockEntity.tick(level, pos, state, blockEntity)
            }
}