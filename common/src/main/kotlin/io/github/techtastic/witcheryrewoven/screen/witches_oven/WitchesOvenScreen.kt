package io.github.techtastic.witcheryrewoven.screen.witches_oven

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import io.github.techtastic.witcheryrewoven.WitcheryRewoven.MOD_ID
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class WitchesOvenScreen(menu: WitchesOvenMenu, inventory: Inventory, title: Component) : AbstractContainerScreen<WitchesOvenMenu>(menu, inventory, title) {
    private val TEXTURE: ResourceLocation = ResourceLocation(MOD_ID, "textures/gui/witches_oven_gui.png")

    override fun init() {
        super.init()
        titleLabelX = (imageWidth - font.width(title)) / 2
    }

    override fun renderBg(ps: PoseStack, partial: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        val x = (width - imageWidth) / 2
        val y = (height - imageHeight) / 2

        this.blit(ps, x, y, 0, 0, imageWidth, imageHeight)

        if (menu.isCrafting())
            blit(ps, x + 62, y + 22, 176, 14, menu.getScaledProgress(), 48)

        if (menu.isConsumingFuel())
            blit(ps, x + 45, y + 37 + 14 - menu.getScaledFuelProgress(), 176,
                    14 - menu.getScaledFuelProgress(), 14, menu.getScaledFuelProgress())
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(poseStack)
        super.render(poseStack, mouseX, mouseY, delta)
        renderTooltip(poseStack, mouseX, mouseY)
    }
}