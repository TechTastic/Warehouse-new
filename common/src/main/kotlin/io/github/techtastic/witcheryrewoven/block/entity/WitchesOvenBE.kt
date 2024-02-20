package io.github.techtastic.witcheryrewoven.block.entity

import io.github.techtastic.witcheryrewoven.block.WRBlockEntities.WITCHES_OVEN
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class WitchesOvenBE(blockPos: BlockPos, blockState: BlockState) : BlockEntity(WITCHES_OVEN.get(), blockPos, blockState) {
}