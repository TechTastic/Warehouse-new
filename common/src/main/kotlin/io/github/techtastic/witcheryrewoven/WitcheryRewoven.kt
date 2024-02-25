package io.github.techtastic.witcheryrewoven

import dev.architectury.registry.client.rendering.RenderTypeRegistry
import dev.architectury.registry.menu.MenuRegistry
import io.github.techtastic.witcheryrewoven.block.WRBlockEntities
import io.github.techtastic.witcheryrewoven.block.WRBlocks
import io.github.techtastic.witcheryrewoven.item.WRItems
import io.github.techtastic.witcheryrewoven.recipe.WRRecipes
import io.github.techtastic.witcheryrewoven.screen.WRMenuTypes
import io.github.techtastic.witcheryrewoven.screen.witches_oven.WitchesOvenScreen
import net.minecraft.client.renderer.RenderType

object WitcheryRewoven {
    const val MOD_ID = "witcheryrewoven"

    fun init() {
        WRItems.register()
        WRBlocks.register()

        WRBlockEntities.register()
        WRMenuTypes.register()

        WRRecipes.register()
    }

    fun initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), WRBlocks.WITCHES_OVEN.get())

        MenuRegistry.registerScreenFactory(WRMenuTypes.WITCHES_OVEN.get(), ::WitchesOvenScreen)
    }
}