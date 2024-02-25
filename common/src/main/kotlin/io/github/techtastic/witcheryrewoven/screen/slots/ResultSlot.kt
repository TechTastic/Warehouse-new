package io.github.techtastic.witcheryrewoven.screen.slots

import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.NotNull

class ResultSlot(container: Container, index: Int, x: Int, y: Int) : Slot(container, index, x, y) {
    override fun mayPlace(@NotNull itemStack: ItemStack): Boolean =
            false
}