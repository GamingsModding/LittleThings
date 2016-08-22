package com.gamingsmod.littlethings.vanity.gui.container;

import com.gamingsmod.littlethings.base.LittleThings;
import com.gamingsmod.littlethings.base.network.GuiHandler;
import com.gamingsmod.littlethings.base.network.MessageHandler;
import com.gamingsmod.littlethings.base.network.message.MessageArmorStandGui;
import com.gamingsmod.littlethings.vanity.container.ContainerArmorStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiArmorStand extends GuiContainer
{
    private IInventory playerInventory;
    private EntityArmorStand armorStand;
    private EntityPlayer player;
    private GuiButton[] buttons = new GuiButton[4];
    private float oldMouseX;
    private float oldMouseY;

    public GuiArmorStand(IInventory playerInventory, final EntityArmorStand armorStand, EntityPlayer player)
    {
        super(new ContainerArmorStand(playerInventory, armorStand, player));
        this.xSize = 176;
        this.ySize = 166;
        this.allowUserInput = false;

        this.player = player;
        this.playerInventory = playerInventory;
        this.armorStand = armorStand;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("littlethings:textures/gui/armor_stand.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        GuiInventory.drawEntityOnScreen(i + 51, j + 70, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.armorStand);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
        String armsVisible = this.armorStand.getShowArms() ? "Yes" : "No";
        String basePlate = this.armorStand.hasNoBasePlate() ? "No" : "Yes";
        String small = this.armorStand.isSmall() ? "S" : "L";
        buttons[0].displayString = "Arms: " + armsVisible;
        buttons[1].displayString = "Base Plate: " + basePlate;
        buttons[2].displayString = small;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        String armsVisible = this.armorStand.getShowArms() ? "Yes" : "No";
        String basePlate = this.armorStand.hasNoBasePlate() ? "No" : "Yes";
        String small = this.armorStand.isSmall() ? "S" : "L";
        buttonList.add(buttons[0] = new GuiButton(0, guiLeft + 93, guiTop + 10, 80, 20, "Arms: " + armsVisible));
        buttonList.add(buttons[1] = new GuiButton(1, guiLeft + 93, guiTop + 30, 80, 20, "Base Plate: " + basePlate));
        buttonList.add(buttons[2] = new GuiButton(2, guiLeft - 20, guiTop + 10, 20, 20, small));
        buttonList.add(buttons[3] = new GuiButton(3, guiLeft - 20, guiTop + 30, 20, 20, "R"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        int id = armorStand.getEntityId();
        if (button == buttons[0]) {
            // Show/Hide arms
            MessageHandler.INSTANCE.sendToServer(new MessageArmorStandGui(id, 4, !armorStand.getShowArms()));
        } else if (button == buttons[1]) {
            // Show/Hide bottom plate
            MessageHandler.INSTANCE.sendToServer(new MessageArmorStandGui(id, 8, !armorStand.hasNoBasePlate()));
        } else if (button == buttons[2]) {
            // Large/Small
            MessageHandler.INSTANCE.sendToServer(new MessageArmorStandGui(id, 1, !armorStand.isSmall()));
        } else if (button == buttons[3]) {
            // Display rotations screen
            Minecraft.getMinecraft().thePlayer.openGui(LittleThings.instance, GuiHandler.ARMOR_STAND_ROTATION, armorStand.worldObj, (int) armorStand.posX, (int) armorStand.posY, (int) armorStand.posZ);
        } else {
            throw new IOException("WTF just happened? (Unknown button pressed)");
        }
    }
}
