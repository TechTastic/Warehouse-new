package io.github.techtastic.witcheryrewoven

import io.github.techtastic.witcheryrewoven.block.WRBlockEntities
import io.github.techtastic.witcheryrewoven.block.WRBlocks
import io.github.techtastic.witcheryrewoven.item.WRItems

object WitcheryRewoven {
    const val MOD_ID = "witcheryrewoven"

    fun init() {
        WRItems.register()
        WRBlocks.register()
        WRBlockEntities.register()
    }

    fun initClient() {
    }
}