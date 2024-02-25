package io.github.techtastic.witcheryrewoven.screen.slots

import io.github.techtastic.witcheryrewoven.item.WRItems
import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.NotNull

class JarSlot(container: Container, index: Int, x: Int, y: Int) : Slot(container, index, x, y) {
    override fun mayPlace(@NotNull itemStack: ItemStack): Boolean {
        if (!itemStack.`is`(WRItems.CLAY_JAR.get()))
            return false

        return !(container.getItem(0).isEmpty ||
                container.getItem(0).`is`(itemStack.item) &&
                container.getItem(0).count < container.maxStackSize)
    }
}