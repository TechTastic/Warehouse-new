package io.github.techtastic.witcheryrewoven.block.custom

import io.github.techtastic.witcheryrewoven.block.entity.WitchesOvenBE
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class WitchesOvenBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = WitchesOvenBE(pos, state)

    @Deprecated("Deprecated in Java", ReplaceWith("RenderShape.MODEL", "net.minecraft.world.level.block.RenderShape"))
    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.MODEL
}