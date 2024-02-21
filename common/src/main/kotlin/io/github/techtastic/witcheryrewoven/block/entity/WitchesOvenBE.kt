package io.github.techtastic.witcheryrewoven.block.entity

import io.github.techtastic.witcheryrewoven.block.WRBlockEntities.WITCHES_OVEN
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class WitchesOvenBE(pos: BlockPos, state: BlockState) : BlockEntity(WITCHES_OVEN.get(), pos, state) {
    val inventory = listOf(
            ItemStack.EMPTY,
            ItemStack.EMPTY,
            ItemStack.EMPTY,
            ItemStack.EMPTY,
            ItemStack.EMPTY
    )
}