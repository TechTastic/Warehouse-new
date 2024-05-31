package io.github.techtastic.warehouse.screen.controller

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.Button
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class AABBMovementButton(x: Int, y: Int, text: Component, val labelX: Int, val labelY: Int, val texture: ResourceLocation, onPress: OnPress):
        Button(x, y, 9, 9, text, onPress) {
    var isPressed = false

    init {
        active = true
    }

    override fun renderButton(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, texture)

        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.enableDepthTest()

        if (this.isPressed || !this.active)
            this.blit(poseStack, x, y, 185, 0, 9, 9)
        else
            this.blit(poseStack, x, y, 176, 0, 9, 9)

        this.blit(poseStack, x + 2, y + 2, labelX, labelY, 5, 5)
    }

    override fun onClick(mouseX: Double, mouseY: Double) {
        isPressed = true
        super.onClick(mouseX, mouseY)
    }

    override fun onRelease(mouseX: Double, mouseY: Double) {
        isPressed = false
    }
}