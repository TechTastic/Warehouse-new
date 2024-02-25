package io.github.techtastic.witcheryrewoven.screen.witches_oven

import io.github.techtastic.witcheryrewoven.block.WRBlocks
import io.github.techtastic.witcheryrewoven.block.entity.WitchesOvenBE
import io.github.techtastic.witcheryrewoven.screen.WRMenuTypes
import io.github.techtastic.witcheryrewoven.screen.slots.FuelSlot
import io.github.techtastic.witcheryrewoven.screen.slots.JarSlot
import io.github.techtastic.witcheryrewoven.screen.slots.ResultSlot
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.*
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.NotNull

class WitchesOvenMenu(id: Int, private val inventory: Inventory, private val oven: WitchesOvenBE, private val data: ContainerData):
        AbstractContainerMenu(WRMenuTypes.WITCHES_OVEN.get(), id) {
    constructor(id: Int, inventory: Inventory, extraData: FriendlyByteBuf):
            this(id, inventory, inventory.player.level.getBlockEntity(extraData.readBlockPos()) as WitchesOvenBE, SimpleContainerData(4))

    init {
        checkContainerSize(inventory, 5);

        addPlayerInventory(inventory)
        addPlayerHotbar(inventory)

        addSlot(Slot(oven, 0, 44, 19))
        addSlot(FuelSlot(oven, 1, 44, 54))
        addSlot(JarSlot(oven, 2, 80, 54))
        addSlot(ResultSlot(oven, 3, 116, 19))
        addSlot(ResultSlot(oven, 4, 116, 54))

        addDataSlots(data)
    }

    fun isCrafting(): Boolean = data[0] > 0
    fun getScaledProgress(): Int {
        val progress = data[0]
        val maxProgress = data[1] // Max Progress

        val progressArrowSize = 48 // This is the height in pixels of your arrow

        return if (maxProgress != 0 && progress != 0) progress * progressArrowSize / maxProgress else 0
    }
    fun isConsumingFuel(): Boolean = data.get(2) > 0
    fun getScaledFuelProgress(): Int {
        val fuelTime = data[2]
        val maxFuelTime = data[3] // Max Progress

        val progressFlameSize = 14 // This is the height in pixels of your arrow

        return if (maxFuelTime != 0) (fuelTime / maxFuelTime * progressFlameSize) else 0
    }

    override fun quickMoveStack(@NotNull player: Player, index: Int): ItemStack {
        val sourceSlot: Slot = slots[index]
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY //EMPTY_ITEM
        val sourceStack: ItemStack = sourceSlot.item
        val copyOfSourceStack: ItemStack = sourceStack.copy()

        // Check if the slot clicked is one of the vanilla container slots
        if (index < 36) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, 36, 40, false)) {
                return ItemStack.EMPTY // EMPTY_ITEM
            }
        } else if (index > 36) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, 0, 35, false)) {
                return ItemStack.EMPTY
            }
        } else {
            println("Invalid slotIndex:$index")
            return ItemStack.EMPTY
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.count == 0) {
            sourceSlot.set(ItemStack.EMPTY)
        } else {
            sourceSlot.setChanged()
        }
        sourceSlot.onTake(player, sourceStack)
        return copyOfSourceStack
    }

    override fun stillValid(@NotNull player: Player): Boolean =
            stillValid(ContainerLevelAccess.create(player.level, oven.blockPos), player, WRBlocks.WITCHES_OVEN.get())

    private fun addPlayerInventory(playerInventory: Inventory) {
        for (i in 0..2) {
            for (l in 0..8) {
                addSlot(Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18))
            }
        }
    }

    private fun addPlayerHotbar(playerInventory: Inventory) {
        for (i in 0..8) {
            addSlot(Slot(playerInventory, i, 8 + i * 18, 144))
        }
    }
}