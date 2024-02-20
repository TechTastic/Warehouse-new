package io.github.techtastic.witcheryrewoven.forge

import dev.architectury.platform.forge.EventBuses
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.KotlinModLoadingContext
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.MOD_ID
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.init
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.initClient
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@Mod(MOD_ID)
class WitcheryRewovenForge {
    val MOD_BUS = KotlinModLoadingContext.get().getKEventBus()

    init {
        EventBuses.registerModEventBus(MOD_ID, MOD_BUS)

        MOD_BUS.addListener(::clientSetup)

        init()
    }

    fun clientSetup(event: FMLClientSetupEvent) {
        initClient()
    }
}