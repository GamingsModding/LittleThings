package com.gamingsmod.littlethings.vanity.addition;

import com.gamingsmod.littlethings.base.LittleThings;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.base.network.GuiHandler;
import com.gamingsmod.littlethings.base.network.MessageHandler;
import com.gamingsmod.littlethings.base.network.message.MessageGuiRecipeConflictPlayer;
import com.gamingsmod.littlethings.decoration.block.BlockVanillaCraftingTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class NoConflictRecipes extends Addition
{
    private static int currentResult;
    private final int PREV_BUTTON_ID = 1001;
    private final int NEXT_BUTTON_ID = 1002;

    public static void updateContainer(ContainerPlayer container, World world, int result)
    {
        int original = currentResult;
        if (result < 0) currentResult -= 1;
        else currentResult += 1;

        List<ItemStack> results = getResults(container.craftMatrix, world);
        if (results.size() <= 1)
            return;

        if (currentResult >= results.size() || currentResult < 0) {
            currentResult = original;
        }

        container.craftResult.setInventorySlotContents(0, results.get(currentResult));
    }

    public static List<ItemStack> getResults(InventoryCrafting craftMatrix, World worldObj)
    {
        List<ItemStack> results = new ArrayList<>();
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        recipes.forEach(iRecipe -> {
            if (iRecipe.matches(craftMatrix, worldObj)) {
                results.add(iRecipe.getRecipeOutput().copy());
            }
        });

        return results;
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();
        BlockPos pos = event.getPos();

        if (world != null && !world.isRemote && !player.isSneaking()) {
            IBlockState block = world.getBlockState(pos);
            if (block.getBlock() == Blocks.CRAFTING_TABLE || block.getBlock() instanceof BlockVanillaCraftingTable) {
                player.openGui(LittleThings.instance, GuiHandler.CRAFTING_TABLE_NO_CONFLICT, world, pos.getX(), pos.getY(), pos.getZ());
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void playerInventory(GuiScreenEvent.InitGuiEvent.Post event)
    {
        if (event.getGui() instanceof GuiInventory) {
            currentResult = 0;
            GuiContainer guiInv = (GuiContainer) event.getGui();

            int guiLeft = (guiInv.width - 176) / 2;
            int guiTop = (guiInv.height - 166) / 2;

            Container container = guiInv.inventorySlots;

            int x = 0;
            int y = 0;
            for (Slot slot : container.inventorySlots) {
                if (slot instanceof SlotCrafting) {
                    x = slot.xDisplayPosition;
                    y = slot.yDisplayPosition;
                    break;
                }
            }

            event.getButtonList().add(new GuiButton(PREV_BUTTON_ID, guiLeft + x - 2, guiTop + y + 20, 10, 15, "<"));
            event.getButtonList().add(new GuiButton(NEXT_BUTTON_ID, guiLeft + x + 8, guiTop + y + 20, 10, 15, ">"));
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void buttonPressed(GuiScreenEvent.ActionPerformedEvent.Post event)
    {
        if (event.getGui() instanceof GuiInventory) {
            GuiContainer guiInv = (GuiContainer) event.getGui();
            GuiButton button = event.getButton();
            World world = Minecraft.getMinecraft().theWorld;
            if (button.id == PREV_BUTTON_ID) {
                updateContainer((ContainerPlayer) guiInv.inventorySlots, world, -1);
                MessageHandler.INSTANCE.sendToServer(new MessageGuiRecipeConflictPlayer(currentResult));
            } else if (button.id == NEXT_BUTTON_ID) {
                updateContainer((ContainerPlayer) guiInv.inventorySlots, world, 1);
                MessageHandler.INSTANCE.sendToServer(new MessageGuiRecipeConflictPlayer(currentResult));
            }
        }
    }

    @Override
    public boolean hasSubscriptions()
    {
        return true;
    }
}
