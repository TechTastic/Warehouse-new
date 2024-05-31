package io.github.techtastic.warehouse.mixin;

import io.github.techtastic.warehouse.util.IWarehouseStorage;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RandomizableContainerBlockEntity.class)
public class MixinRandomizableContainerBlockEntity implements IWarehouseStorage {
    @NotNull
    @Override
    public Container getContainer() {
        return ((Container)((Object) this));
    }
}
