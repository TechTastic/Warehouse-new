package io.github.techtastic.witcheryrewoven.block

import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.MOD_ID
import io.github.techtastic.witcheryrewoven.block.custom.WitchesOvenBlock
import io.github.techtastic.witcheryrewoven.item.WRItems.TAB
import net.minecraft.core.Registry
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material

object WRBlocks {
    private val ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY)
    private val BLOCKS = DeferredRegister.create(MOD_ID, Registry.BLOCK_REGISTRY)
    private val BLOCKS_WITHOUT_ITEMS = DeferredRegister.create(MOD_ID, Registry.BLOCK_REGISTRY)

    val WITCHES_OVEN = BLOCKS.register("witches_oven") {
        WitchesOvenBlock(BlockBehaviour.Properties.of(Material.METAL))
    }

    fun register() {
        BLOCKS.register()
        BLOCKS_WITHOUT_ITEMS.register()

        BLOCKS.forEach {
            ITEMS.register(it.id) { BlockItem(it.get(), Item.Properties().tab(TAB)) }
        }

        ITEMS.register()
    }
}