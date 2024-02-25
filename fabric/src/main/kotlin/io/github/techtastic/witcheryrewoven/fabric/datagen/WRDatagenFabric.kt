package io.github.techtastic.witcheryrewoven.fabric.datagen

import io.github.techtastic.witcheryrewoven.datagen.WRCommonDatagen
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class WRDatagenFabric: DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        gen.addProvider(::WRRecipeGenerator)
    }
}