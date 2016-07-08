package com.gamingsmod.littlethings.decoration.container;

import com.gamingsmod.littlethings.decoration.addition.VanillaCraftingTables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ContainerCraftingTable extends ContainerWorkbench
{
    private World worldObj;
    private BlockPos pos;
    private int currentResult = 0;

    public ContainerCraftingTable(InventoryPlayer playerInventory, World worldIn, BlockPos posIn)
    {
        super(playerInventory, worldIn, posIn);
        worldObj = worldIn;
        pos = posIn;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        super.onCraftMatrixChanged(inventoryIn);
        currentResult = 0;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return (this.worldObj.getBlockState(this.pos).getBlock() == VanillaCraftingTables.crafting_table || this.worldObj.getBlockState(this.pos).getBlock() == Blocks.CRAFTING_TABLE) && playerIn.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void setCurrentResult(int result)
    {
        int original = currentResult;
        if (result < 0) currentResult -= 1;
        else currentResult += 1;

        List<ItemStack> results = getResults();
        if (results.size() <= 1)
            return;

        if (currentResult >= results.size() || currentResult < 0) {
            currentResult = original;
        }
        craftResult.setInventorySlotContents(0, results.get(currentResult));
    }

    private List<ItemStack> getResults()
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
}
