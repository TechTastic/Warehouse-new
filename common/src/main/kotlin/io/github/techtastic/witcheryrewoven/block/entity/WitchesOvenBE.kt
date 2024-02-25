package io.github.techtastic.witcheryrewoven.block.entity

import dev.architectury.registry.fuel.FuelRegistry
import dev.architectury.registry.menu.ExtendedMenuProvider
import io.github.techtastic.witcheryrewoven.block.WRBlockEntities.WITCHES_OVEN
import io.github.techtastic.witcheryrewoven.item.WRItems
import io.github.techtastic.witcheryrewoven.recipe.custom.WitchesOvenRecipe
import io.github.techtastic.witcheryrewoven.screen.witches_oven.WitchesOvenMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.tags.ItemTags
import net.minecraft.world.ContainerHelper
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*
import kotlin.random.Random


class WitchesOvenBE(pos: BlockPos, state: BlockState) : BlockEntity(WITCHES_OVEN.get(), pos, state), WorldlyContainer, ExtendedMenuProvider {
    private val inventory: NonNullList<ItemStack> = NonNullList.withSize(5, ItemStack.EMPTY)
    private var progress = 0
    private var maxProgress = 200
    private var fuelTime = 0
    private var maxFuelTime = 0
    private val data = object : ContainerData {
        override fun get(i: Int): Int = when (i) {
            0 -> this@WitchesOvenBE.progress
            1 -> this@WitchesOvenBE.maxProgress
            2 -> this@WitchesOvenBE.fuelTime
            3 -> this@WitchesOvenBE.maxFuelTime
            else -> 0
        }

        override fun set(i: Int, j: Int) = when (i) {
            0 -> this@WitchesOvenBE.progress = j
            1 -> this@WitchesOvenBE.maxProgress = j
            2 -> this@WitchesOvenBE.fuelTime = j
            3 -> this@WitchesOvenBE.maxFuelTime = j
            else -> {}
        }

        override fun getCount(): Int = 4
    }

    // Functionality

    companion object {
        @JvmStatic
        fun tick(level: Level, pos: BlockPos, state: BlockState, oven: WitchesOvenBE) {
            if (oven.isConsumingFuel())
                oven.fuelTime--

            if (!oven.hasRecipe()) {
                oven.resetProgress()
                oven.setChanged()
                return
            }

            if (oven.hasAvailableFuel() && !oven.isConsumingFuel())
                oven.consumeFuel()

            if (!oven.isConsumingFuel())
                return

            oven.progress++
            if (oven.progress > oven.maxProgress) {
                oven.craftItem()
                oven.resetProgress()
            }

            oven.setChanged()
        }
    }

    fun isConsumingFuel(): Boolean =
            this.fuelTime > 0
    fun hasAvailableFuel(): Boolean =
            FuelRegistry.get(getItem(1)) > 0
    fun consumeFuel() {
        this.fuelTime = FuelRegistry.get(removeItem(1, 1))
        this.maxFuelTime = this.fuelTime
        setChanged()
    }
    fun resetProgress() {
        this.progress = 0
    }
    private fun hasJar(): Boolean =
            getItem(2).`is`(WRItems.CLAY_JAR.get())
    fun hasRecipe(): Boolean {
        val match = level?.let { it.recipeManager.getRecipeFor(WitchesOvenRecipe.Type.INSTANCE, this, it) } ?: Optional.empty()
        val smoking = level?.let { it.recipeManager.getRecipeFor(RecipeType.SMOKING, this, it) } ?: Optional.empty()

        if (match.isEmpty && smoking.isEmpty) return false

        var defaultMaxProgress = 200
        var canOutput = false

        // If the recipe is food or logs, 10% quicker
        smoking.ifPresent {
            defaultMaxProgress -= 20
            canOutput = canOutputInSlot(3, it.resultItem)
        }
        match.ifPresent {
            if (it.ingredients[0].items[0].`is`(ItemTags.LOGS))
                defaultMaxProgress -= 20
            canOutput = canOutputInSlot(3, it.resultItem)
        }

        if (this.maxProgress != defaultMaxProgress) {
            this.maxProgress = defaultMaxProgress
            setChanged()
        }

        return canOutput
    }
    fun craftItem() {
        val match = level?.let { it.recipeManager.getRecipeFor(WitchesOvenRecipe.Type.INSTANCE, this, it) } ?: Optional.empty()
        val smoking = level?.let { it.recipeManager.getRecipeFor(RecipeType.SMOKING, this, it) } ?: Optional.empty()

        if (match.isEmpty && smoking.isEmpty) return

        removeItem(0, 1)

        match.ifPresent {
            outputToSlot(3, it.resultItem)
            createJarOutput(it.getJarItem())
        }
        smoking.ifPresent {
            outputToSlot(3, it.resultItem)
            createJarOutput(ItemStack(WRItems.FOUL_FUME.get()))
        }
    }
    private fun createJarOutput(jar: ItemStack) {
        val chance = 0.1

        if (!hasJar() || Random.nextDouble(1.0) > chance || !canOutputInSlot(4, jar))
            return

        outputToSlot(4, ItemStack(jar.item))
    }
    private fun canOutputInSlot(slot: Int, stack: ItemStack): Boolean = getItem(slot).isEmpty ||
            (getItem(slot).maxStackSize > getItem(slot).count && getItem(slot).sameItem(stack))

    private fun outputToSlot(slot: Int, stack: ItemStack) {
        val slotStack = getItem(slot)
        if (slotStack.isEmpty)
            setItem(slot, stack)
        slotStack.grow(stack.count)
    }

    // NBT Data

    override fun saveAdditional(compoundTag: CompoundTag) {
        ContainerHelper.saveAllItems(compoundTag, this.inventory)

        compoundTag.putIntArray("WitcheryRewoven\$internal", listOf(this.progress, this.fuelTime, this.maxFuelTime))

        super.saveAdditional(compoundTag)
    }

    override fun load(compoundTag: CompoundTag) {
        super.load(compoundTag)

        ContainerHelper.loadAllItems(compoundTag, this.inventory)

        val internal = compoundTag.getIntArray("WitcheryRewoven\$internal")
        this.progress = internal[0]
        this.fuelTime = internal[1]
        this.maxFuelTime = internal[2]
    }

    // GUI Stuffs

    override fun createMenu(id: Int, inventory: Inventory, player: Player): AbstractContainerMenu =
            WitchesOvenMenu(id, inventory, this, this.data)

    override fun getDisplayName(): Component =
            TranslatableComponent("block.witcheryrewoven.witches_oven")

    override fun saveExtraData(buf: FriendlyByteBuf) {
        buf.writeBlockPos(this.blockPos)
    }

    // Container Stuffs

    override fun clearContent() =
            this.inventory.clear()

    override fun getContainerSize(): Int =
            this.inventory.size

    override fun isEmpty(): Boolean {
        for (i in 0 until this.containerSize)
            if (!getItem(i).isEmpty)
                return false
        return true
    }

    override fun getItem(i: Int): ItemStack =
            this.inventory[i]

    override fun removeItem(i: Int, j: Int): ItemStack {
        val stack = ContainerHelper.removeItem(this.inventory, i, j)
        if (!stack.isEmpty)
            setChanged()
        return stack
    }

    override fun removeItemNoUpdate(i: Int): ItemStack =
            ContainerHelper.takeItem(this.inventory, i)

    override fun setItem(i: Int, stack: ItemStack) {
        this.inventory[i] = stack
        if (stack.count > this.maxStackSize)
            stack.count = this.maxStackSize
    }

    override fun stillValid(player: Player): Boolean =
            true

    override fun getSlotsForFace(direction: Direction): IntArray {
        val result = IntArray(this.containerSize)
        for (i in result.indices) {
            result[i] = i
        }

        return result
    }

    override fun canPlaceItemThroughFace(i: Int, itemStack: ItemStack, direction: Direction?): Boolean = when (i) {
        0 -> true
        1 -> FuelRegistry.get(itemStack) > 0
        2 -> itemStack.`is`(WRItems.CLAY_JAR.get())
        else -> false
    }

    override fun canTakeItemThroughFace(i: Int, itemStack: ItemStack, direction: Direction): Boolean = when (i) {
        3, 4 -> true
        else -> false
    }
}