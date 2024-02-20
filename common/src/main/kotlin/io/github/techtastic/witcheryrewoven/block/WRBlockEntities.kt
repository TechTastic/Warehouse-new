package io.github.techtastic.witcheryrewoven.block

import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.MOD_ID
import io.github.techtastic.witcheryrewoven.block.entity.WitchesOvenBE
import net.minecraft.core.Registry
import net.minecraft.world.level.block.entity.BlockEntityType
import io.github.techtastic.witcheryrewoven.block.WRBlocks

object WRBlockEntities {
    private val BLOCK_ENTITIES = DeferredRegister.create(MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY)

    val WITCHES_OVEN = BLOCK_ENTITIES.register("witches_oven") {
        BlockEntityType.Builder.of(::WitchesOvenBE, WRBlocks.WITCHES_OVEN.get()).build(null)
    }

    fun register() {
        BLOCK_ENTITIES.register()
    }
}