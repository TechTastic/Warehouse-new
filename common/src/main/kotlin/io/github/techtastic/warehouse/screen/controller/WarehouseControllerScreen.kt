package io.github.techtastic.warehouse.screen.controller

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import io.github.techtastic.warehouse.Warehouse.MOD_ID
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class WarehouseControllerScreen(menu: WarehouseControllerMenu, inventory: Inventory, title: Component) :
        AbstractContainerScreen<WarehouseControllerMenu>(menu, inventory, title) {
    private val TEXTURE = ResourceLocation(MOD_ID, "textures/gui/warehouse_controller.png")
    var search: EditBox? = null

    init {
        this.imageWidth = 219
        this.imageHeight = 222
        this.inventoryLabelY += 55
    }

    override fun containerTick() {
        this.search?.tick()
        super.containerTick()
    }

    override fun init() {
        super.init()
        val x = (width - imageWidth) / 2
        val y = (height - imageHeight) / 2

        this.addRenderableWidget(this.getOrCreateSearchBox(x, y))

        //addXButtons(x, y)
        //addYButtons(x, y)
        //addZButtons(x, y)
    }

    override fun renderBg(poseStack: PoseStack, partialTick: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        val x = (width - imageWidth) / 2
        val y = (height - imageHeight) / 2
        this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight)

        //drawMovementNumbers(poseStack, x + 17, y + 118)
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(poseStack)
        super.render(poseStack, mouseX, mouseY, delta)
    }

    // mojank doesn't check mouse release for their widgets for some reason
    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        isDragging = false
        if (getChildAt(mouseX, mouseY).filter { it.mouseReleased(mouseX, mouseY, button) }.isPresent) return true

        return super.mouseReleased(mouseX, mouseY, button)
    }

    private fun getOrCreateSearchBox(x: Int, y: Int): EditBox {
        if (this.search == null) {
            this.search = EditBox(this.font, x + 81, y + 128, 80, 8, TranslatableComponent("block.warehouse.warehouse_controller.search"))
            this.search!!.setMaxLength(50)
            this.search!!.setBordered(false)
            //this.search!!.isVisible = true
            this.search!!.setTextColor(16777215)
            this.search!!.setResponder(this::trySearch)
        }

        return this.search!!
    }

    private fun trySearch(text: String) {
        println(text)
    }

    private fun addAABBButtons(x: Int, y: Int) {

    }

    /*private fun addXButtons(x: Int, y: Int) {
        addRenderableWidget(AABBMovementButton(x + 12, y + 107, TextComponent("+X"),194, 0, TEXTURE) {
            minecraft?.gameMode?.handleInventoryButtonClick(menu.containerId, 4)
        })

        addRenderableWidget(AABBMovementButton(x + 12, y + 128, TextComponent("-X"), 199, 0, TEXTURE) {
            minecraft?.gameMode?.handleInventoryButtonClick(menu.containerId, 5)
        })
    }

    private fun addYButtons(x: Int, y: Int) {
        addRenderableWidget(AABBMovementButton(x + 26, y + 107, TextComponent("+Y"), 204, 0, TEXTURE) {
            minecraft?.gameMode?.handleInventoryButtonClick(menu.containerId, 2)
        })

        addRenderableWidget(AABBMovementButton(x + 26, y + 128, TextComponent("-Y"), 209, 0, TEXTURE) {
            minecraft?.gameMode?.handleInventoryButtonClick(menu.containerId, 3)
        })
    }

    private fun addZButtons(x: Int, y: Int) {
        addRenderableWidget(AABBMovementButton(x + 40, y + 107, TextComponent("+Z"), 214, 0, TEXTURE) {
            minecraft?.gameMode?.handleInventoryButtonClick(menu.containerId, 0)
        })

        addRenderableWidget(AABBMovementButton(x + 40, y + 128, TextComponent("-Z"), 219, 0, TEXTURE) {
            minecraft?.gameMode?.handleInventoryButtonClick(menu.containerId, 1)
        })
    }

    fun drawMovementNumbers(poseStack: PoseStack, x: Int, y: Int) {
        drawCenteredString(poseStack, this.font, "X", x, y, 0xFF0000)
        drawCenteredString(poseStack, this.font, "Y", x + 14, y, 0x00FF00)
        drawCenteredString(poseStack, this.font, "Z", x + 28, y, 0x0000FF)
    }*/
}