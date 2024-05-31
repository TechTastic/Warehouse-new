package io.github.techtastic.warehouse.fabric

import io.github.techtastic.warehouse.Warehouse.init
import io.github.techtastic.warehouse.Warehouse.initClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer

class WarehouseFabric: ModInitializer {
    override fun onInitialize() {
        init()
    }

    class Client: ClientModInitializer {
        override fun onInitializeClient() {
            initClient()
        }
    }
}