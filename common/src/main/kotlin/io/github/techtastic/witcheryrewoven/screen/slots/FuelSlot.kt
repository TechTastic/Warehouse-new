package io.github.techtastic.witcheryrewoven.screen.slots

import dev.architectury.registry.fuel.FuelRegistry
import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.jetbrains.annotations.NotNull

class FuelSlot(container: Container, index: Int, x: Int, y: Int) : Slot(container, index, x, y) {
    override fun mayPlace(@NotNull itemStack: ItemStack): Boolean {
        if (FuelRegistry.get(itemStack) == 0)
            return false

        return !(container.getItem(0).isEmpty ||
                container.getItem(0).`is`(itemStack.item) &&
                container.getItem(0).count < container.maxStackSize)
    }

    override fun getMaxStackSize(@NotNull itemStack: ItemStack): Int {
        return if (isBucket(itemStack)) 1 else super.getMaxStackSize(itemStack)
    }

    private fun isBucket(stack: ItemStack): Boolean {
        return stack.`is`(Items.BUCKET)
    }
}