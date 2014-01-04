package mcdelta.tuxweapons.item;

import java.awt.Color;
import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.tuxweapons.TWContent;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBolt extends ItemTW implements IExtraPasses
{
     @SideOnly (Side.CLIENT)
     private Icon potionIcon;
     
     @SideOnly (Side.CLIENT)
     private Icon potionIconOverlay;
     
     
     
     
     public ItemBolt ()
     {
          super("bolt");
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (final ItemStack stack, final World world, final EntityPlayer player)
     {
          player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
          return stack;
     }
     
     
     
     
     @Override
     public EnumAction getItemUseAction (final ItemStack stack)
     {
          return EnumAction.block;
     }
     
     
     
     
     @Override
     public void registerIcons (final IconRegister register)
     {
          final String s = this.name.replace(".", "_");
          
          this.itemIcon = this.doRegister(s, register);
          this.potionIcon = this.doRegister(s + "_potion_1", register);
          this.potionIconOverlay = this.doRegister(s + "_potion_2", register);
     }
     
     
     
     
     @Override
     public int getMaxItemUseDuration (final ItemStack stack)
     {
          return 72000;
     }
     
     
     
     
     @Override
     public ItemStack onEaten (final ItemStack stack, final World world, final EntityPlayer player)
     {
          return stack;
     }
     
     
     
     
     @Override
     public void onPlayerStoppedUsing (ItemStack itemStack, final World world, final EntityPlayer player, final int extraInfo)
     {
          InventoryPlayer.getHotbarSize();
          Item item;
          ItemStack stack;
          NBTTagList tagList = new NBTTagList();
          
          if (itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey("CustomPotionEffects"))
          {
               tagList = (NBTTagList) itemStack.stackTagCompound.getTagList("CustomPotionEffects").copy();
          }
          
          for (int i = 0; i < InventoryPlayer.getHotbarSize(); i++)
          {
               if (player.inventory.getStackInSlot(i) != null)
               {
                    item = player.inventory.getStackInSlot(i).getItem();
                    
                    if (item == TWContent.crossBow)
                    {
                         stack = player.inventory.getStackInSlot(i);
                         
                         final NBTTagCompound nbt = stack.getTagCompound();
                         
                         if (nbt == null)
                         {
                              ItemCrossbow.setNBT(false, stack);
                              
                              if (!player.capabilities.isCreativeMode)
                              {
                                   itemStack.stackSize -= 1;
                                   
                                   if (itemStack.stackSize <= 0)
                                   {
                                        itemStack = null;
                                        player.destroyCurrentEquippedItem();
                                   }
                              }
                              
                              ItemCrossbow.load(player.inventory.getStackInSlot(i), tagList);
                         }
                         
                         if (nbt != null)
                         {
                              if (nbt.getBoolean("Loaded") == false)
                              {
                                   if (!player.capabilities.isCreativeMode)
                                   {
                                        itemStack.stackSize -= 1;
                                        
                                        if (itemStack.stackSize <= 0)
                                        {
                                             itemStack = null;
                                             player.destroyCurrentEquippedItem();
                                        }
                                   }
                                   
                                   ItemCrossbow.load(player.inventory.getStackInSlot(i), tagList);
                                   
                                   return;
                              }
                         }
                    }
               }
          }
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void addInformation (final ItemStack stack, final EntityPlayer player, final List list, final boolean extraInfo)
     {
          Item.potion.addInformation(stack, player, list, extraInfo);
     }
     
     
     
     
     private boolean isPotionArrow (final ItemStack stack)
     {
          if (stack.stackTagCompound == null)
          {
               stack.stackTagCompound = new NBTTagCompound();
          }
          
          return stack.stackTagCompound.hasKey("CustomPotionEffects");
     }
     
     
     
     
     @Override
     public int getPasses (final ItemStack stack)
     {
          if (this.isPotionArrow(stack))
          {
               return 2;
          }
          
          return 1;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (final ItemStack stack, final int pass)
     {
          if (this.isPotionArrow(stack))
          {
               if (pass == 1)
               {
                    return this.potionIconOverlay;
               }
               
               return this.potionIcon;
          }
          
          return this.itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (final ItemStack stack, final int pass)
     {
          if (this.isPotionArrow(stack))
          {
               if (pass == 1)
               {
                    final List<PotionEffect> effects = Item.potion.getEffects(stack);
                    
                    if (effects != null)
                    {
                         final float[] r = new float[effects.size()];
                         final float[] g = new float[effects.size()];
                         final float[] b = new float[effects.size()];
                         
                         for (int i = 0; i < effects.size(); i++)
                         {
                              final PotionEffect effect = effects.get(i);
                              final float[] rgb = Assets.hexToRGB(Potion.potionTypes[effect.getPotionID()].getLiquidColor());
                              
                              r[i] = rgb[0];
                              g[i] = rgb[1];
                              b[i] = rgb[2];
                         }
                         
                         final float finalR = Assets.average(r);
                         final float finalG = Assets.average(g);
                         final float finalB = Assets.average(b);
                         
                         final Color color = new Color(finalR, finalG, finalB);
                         
                         return color.getRGB();
                    }
               }
               
               return 0xffffff;
          }
          
          return 0xffffff;
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          if (this.isPotionArrow(stack))
          {
               return pass == 1;
          }
          
          return false;
     }
}
