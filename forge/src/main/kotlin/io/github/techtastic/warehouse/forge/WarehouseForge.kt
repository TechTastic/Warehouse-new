package io.github.techtastic.warehouse.forge

import dev.architectury.platform.forge.EventBuses
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.KotlinModLoadingContext
import io.github.techtastic.warehouse.Warehouse.MOD_ID
import io.github.techtastic.warehouse.Warehouse.init
import io.github.techtastic.warehouse.Warehouse.initClient
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@Mod(MOD_ID)
class WarehouseForge {
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