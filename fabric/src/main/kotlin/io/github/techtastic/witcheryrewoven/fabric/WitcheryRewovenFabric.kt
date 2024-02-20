package io.github.techtastic.witcheryrewoven.fabric

import io.github.techtastic.witcheryrewoven.WitcheryRewoven.init
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.initClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer

class WitcheryRewovenFabric: ModInitializer {
    override fun onInitialize() {
        init()
    }

    class Client: ClientModInitializer {
        override fun onInitializeClient() {
            initClient()
        }
    }
}