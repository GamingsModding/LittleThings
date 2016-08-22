package com.gamingsmod.littlethings.vanity.gui;

import com.gamingsmod.littlethings.base.network.MessageHandler;
import com.gamingsmod.littlethings.base.network.message.MessageArmorStandRotation;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Rotations;

import java.io.IOException;

public class GuiArmorStandRotation extends GuiScreen
{
    private float[][] values = new float[][]{{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}};
    private GuiSlider[] sliders = new GuiSlider[3];
    private GuiButton[] buttons = new GuiButton[7];
    private EntityArmorStand armorStand;
    private EntityPlayer player;
    private int xSize;
    private int ySize;
    private Responder responder;
    private GuiSlider.FormatHelper formatHelper;
    private int guiLeft;
    private int guiTop;
    private int selectedScreen;

    public GuiArmorStandRotation(EntityArmorStand armorStand, EntityPlayer player)
    {
        this.armorStand = armorStand;
        this.xSize = 255;
        this.ySize = 156;
        this.player = player;
        responder = new Responder();
        formatHelper = (id, name, value) -> name + ": " + (int) value;

        //H,B,RA,LA,RL,LL
        Rotations head = armorStand.getHeadRotation();
        values[0][0] = head.getX();
        values[0][1] = head.getY();
        values[0][2] = head.getZ();

        Rotations body = armorStand.getBodyRotation();
        values[1][0] = body.getX();
        values[1][1] = body.getY();
        values[1][2] = body.getZ();

        Rotations rightArm = armorStand.getRightArmRotation();
        values[2][0] = rightArm.getX();
        values[2][1] = rightArm.getY();
        values[2][2] = rightArm.getZ();

        Rotations leftArm = armorStand.getLeftArmRotation();
        values[3][0] = leftArm.getX();
        values[3][1] = leftArm.getY();
        values[3][2] = leftArm.getZ();

        Rotations rightLeg = armorStand.getRightLegRotation();
        values[4][0] = rightLeg.getX();
        values[4][1] = rightLeg.getY();
        values[4][2] = rightLeg.getZ();

        Rotations leftLeg = armorStand.getLeftLegRotation();
        values[5][0] = leftLeg.getX();
        values[5][1] = leftLeg.getY();
        values[5][2] = leftLeg.getZ();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("littlethings:textures/gui/armor_stand_rotation.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        GuiInventory.drawEntityOnScreen(i + 30, j + 100, 30, i - 180, j - 100, this.armorStand);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui()
    {
        super.initGui();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        this.buttonList.add(sliders[0] = new GuiSlider(responder, 0, guiLeft + 88, guiTop + 40, "X", -180, 180, 0, formatHelper));
        sliders[0].visible = false;
        this.buttonList.add(sliders[1] = new GuiSlider(responder, 1, guiLeft + 88, guiTop + 65, "Y", -180, 180, 0, formatHelper));
        sliders[1].visible = false;
        this.buttonList.add(sliders[2] = new GuiSlider(responder, 2, guiLeft + 88, guiTop + 90, "Z", -180, 180, 0, formatHelper));
        sliders[2].visible = false;

        buttonList.add(buttons[0] = new GuiButton(10, guiLeft + 58, guiTop + 15, 20, 20, "H"));
        buttonList.add(buttons[1] = new GuiButton(11, guiLeft + 58, guiTop + 35, 20, 20, "B"));
        buttonList.add(buttons[2] = new GuiButton(12, guiLeft + 58, guiTop + 55, 20, 20, "RA"));
        buttonList.add(buttons[3] = new GuiButton(13, guiLeft + 58, guiTop + 75, 20, 20, "LA"));
        buttonList.add(buttons[4] = new GuiButton(14, guiLeft + 58, guiTop + 95, 20, 20, "RL"));
        buttonList.add(buttons[5] = new GuiButton(15, guiLeft + 58, guiTop + 115, 20, 20, "LL"));
//        buttonList.add(buttons[6] = new GuiButton(20, guiLeft + 88, guiTop + 115, 150, 20, "Save"));
//        buttons[6].visible = false;
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id >= 10 && button.id < 20) {
            selectedScreen = button.id - 10;

            sliders[0].visible = true;
            sliders[1].visible = true;
            sliders[2].visible = true;

            sliders[0].setSliderValue(values[selectedScreen][0], true);
            sliders[1].setSliderValue(values[selectedScreen][1], true);
            sliders[2].setSliderValue(values[selectedScreen][2], true);
        } else if (button.id == 20) {
        }
    }

    @Override
    public void onGuiClosed()
    {
        for (int i = 0; i < 6; i++) {
            MessageHandler.INSTANCE.sendToServer(new MessageArmorStandRotation(armorStand.getEntityId(), i, values[i][0], values[i][1], values[i][2]));
        }
    }

    private class Responder implements GuiPageButtonList.GuiResponder
    {
        @Override
        public void setEntryValue(int id, boolean value)
        {
            // NO-OP
        }

        @Override
        public void setEntryValue(int id, float value)
        {
            values[selectedScreen][id] = value;
            float x = (id == 0) ? value : values[selectedScreen][0];
            float y = (id == 1) ? value : values[selectedScreen][1];
            float z = (id == 2) ? value : values[selectedScreen][2];
            Rotations newRotation = new Rotations(x, y, z);

            switch (selectedScreen) {
                case 0: //HEAD
                    armorStand.setHeadRotation(newRotation);
                    break;
                case 1: //BODY
                    armorStand.setBodyRotation(newRotation);
                    break;
                case 2: //RIGHT ARM
                    armorStand.setRightArmRotation(newRotation);
                    break;
                case 3: //LEFT ARM
                    armorStand.setLeftArmRotation(newRotation);
                    break;
                case 4: //RIGHT LEG
                    armorStand.setRightLegRotation(newRotation);
                    break;
                case 5: //LEFT LEG
                    armorStand.setLeftLegRotation(newRotation);
                    break;
            }
        }

        @Override
        public void setEntryValue(int id, String value)
        {
            try {
                setEntryValue(id, Float.valueOf(value));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
