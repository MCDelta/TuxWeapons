package mcdelta.tuxweapons.recipe;

import java.util.ArrayList;
import java.util.List;

import mcdelta.tuxweapons.item.ItemTW;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class RecipePotionBolt implements IRecipe
{
     
     @Override
     public boolean matches (InventoryCrafting inv, World world)
     {
          boolean hasPotion = false;
          boolean hasBolt = false;
          
          for (int i = 0; i < inv.getSizeInventory(); i++)
          {
               ItemStack stack = inv.getStackInSlot(i);
               
               if (stack != null)
               {
                    if (stack.getItem() == Item.potion)
                    {
                         hasPotion = true;
                    }
                    
                    if (stack.getItem() == ItemTW.bolt)
                    {
                         hasBolt = true;
                    }
               }
          }
          
          return hasPotion && hasBolt;
     }
     
     
     
     
     @Override
     public ItemStack getCraftingResult (InventoryCrafting inv)
     {
          List<PotionEffect> effects = new ArrayList<PotionEffect>();
          int bolts = 0;
          
          for (int i = 0; i < inv.getSizeInventory(); i++)
          {
               ItemStack stack = inv.getStackInSlot(i);
               
               if (stack != null)
               {
                    if (stack.getItem() == Item.potion)
                    {
                         effects.addAll(Item.potion.getEffects(stack));
                    }
                    
                    if (stack.getItem() == ItemTW.bolt)
                    {
                         bolts++;
                    }
               }
          }
          
          NBTTagList tagList = new NBTTagList();
          
          for (PotionEffect effect : effects)
          {
               PotionEffect newEffect = new PotionEffect(effect);
               newEffect.duration /= bolts;
               tagList.appendTag(newEffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
          }
          
          ItemStack stack = new ItemStack(ItemTW.bolt, bolts);
          
          stack.stackTagCompound = new NBTTagCompound();
          stack.stackTagCompound.setTag("CustomPotionEffects", tagList);
          
          return stack;
     }
     
     
     
     
     @Override
     public int getRecipeSize ()
     {
          return 10;
     }
     
     
     
     
     @Override
     public ItemStack getRecipeOutput ()
     {
          return null;
     }
     
}
