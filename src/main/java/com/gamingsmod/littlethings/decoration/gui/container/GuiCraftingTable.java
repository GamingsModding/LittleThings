package com.gamingsmod.littlethings.decoration.gui.container;

import com.gamingsmod.littlethings.base.network.MessageHandler;
import com.gamingsmod.littlethings.base.network.message.MessageGuiRecipeConflict;
import com.gamingsmod.littlethings.decoration.container.ContainerCraftingTable;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiCraftingTable extends GuiContainer
{
    private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");
    private GuiButton prev;
    private GuiButton next;
    private int currentIndex;

    public GuiCraftingTable(InventoryPlayer playerInv, World worldIn)
    {
        this(playerInv, worldIn, BlockPos.ORIGIN);
    }

    public GuiCraftingTable(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition)
    {
        super(new ContainerCraftingTable(playerInv, worldIn, blockPosition));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(I18n.format("container.crafting"), 28, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

        buttonList.add(next = new GuiButton(0, guiLeft + 135, guiTop + 60, 15, 20, ">"));
        buttonList.add(prev = new GuiButton(1, guiLeft + 115, guiTop + 60, 15, 20, "<"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button == prev) {
            MessageHandler.INSTANCE.sendToServer(new MessageGuiRecipeConflict(-1));
            ContainerCraftingTable container = getContainerCraftingTable();
            if (container == null)
                return;
            container.setCurrentResult(-1);
        } else if (button == next) {
            MessageHandler.INSTANCE.sendToServer(new MessageGuiRecipeConflict(1));
            ContainerCraftingTable container = getContainerCraftingTable();
            if (container == null)
                return;
            container.setCurrentResult(1);
        }
    }

    private ContainerCraftingTable getContainerCraftingTable()
    {
        if (inventorySlots instanceof ContainerCraftingTable)
            return (ContainerCraftingTable) inventorySlots;
        return null;
    }
}
