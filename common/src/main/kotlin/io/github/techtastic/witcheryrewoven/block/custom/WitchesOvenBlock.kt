package io.github.techtastic.witcheryrewoven.block.custom

import dev.architectury.registry.menu.ExtendedMenuProvider
import dev.architectury.registry.menu.MenuRegistry
import io.github.techtastic.witcheryrewoven.block.WRBlockEntities.WITCHES_OVEN
import io.github.techtastic.witcheryrewoven.block.entity.WitchesOvenBE
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult


class WitchesOvenBlock(properties: Properties) : BaseEntityBlock(properties) {
    @Deprecated("Deprecated in Java")
    override fun use(blockState: BlockState, level: Level, blockPos: BlockPos, player: Player, interactionHand: InteractionHand, blockHitResult: BlockHitResult): InteractionResult {
        val result = InteractionResult.sidedSuccess(level.isClientSide)

        if (level.isClientSide)
            return result

        val be = level.getBlockEntity(blockPos)
        if (be !is WitchesOvenBE)
            return result

        getMenuProvider(blockState, level, blockPos)?.let {
            MenuRegistry.openExtendedMenu(player as ServerPlayer, it as ExtendedMenuProvider)
        }

        return result
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
            WitchesOvenBE(pos, state)

    @Deprecated("Deprecated in Java", ReplaceWith("RenderShape.MODEL", "net.minecraft.world.level.block.RenderShape"))
    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.MODEL

    override fun <T : BlockEntity?> getTicker(level: Level, blockState: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? =
            createTickerHelper(blockEntityType, WITCHES_OVEN.get(), WitchesOvenBE::tick)
}