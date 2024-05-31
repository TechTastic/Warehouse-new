package io.github.techtastic.warehouse.util

import net.minecraft.world.Container

interface IWarehouseStorage {
    fun getContainer(): Container
}