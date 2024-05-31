package io.github.techtastic.warehouse.block.entity.renderer

import com.mojang.blaze3d.vertex.PoseStack
import io.github.techtastic.warehouse.block.entity.WarehouseControllerBE
import net.minecraft.client.renderer.LevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class WarehouseControllerBER(context: BlockEntityRendererProvider.Context): BlockEntityRenderer<WarehouseControllerBE> {
    override fun render(warehouse: WarehouseControllerBE, partial: Float, ps: PoseStack, buffer: MultiBufferSource, light: Int, overlay: Int) {
        ps.pushPose()
        LevelRenderer.renderLineBox(ps, buffer.getBuffer(RenderType.lines()),
                warehouse.getAABBForRendering(), 255f, 0f, 0f, 0.8f)
        ps.popPose()
    }
}