package mcdelta.tuxweapons.item;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.HashMultimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBolt extends ItemTW implements IExtraPasses
{
     private int  type;
     
     @SideOnly (Side.CLIENT)
     private Icon potionIcon;
     
     @SideOnly (Side.CLIENT)
     private Icon potionIconOverlay;
     
     
     
     
     public ItemBolt ()
     {
          super("bolt");
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
     {
          player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
          return stack;
     }
     
     
     
     
     @Override
     public EnumAction getItemUseAction (ItemStack stack)
     {
          return EnumAction.block;
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister register)
     {
          String s = this.name.replace(".", "_");
          
          this.itemIcon = doRegister(s, register);
          this.potionIcon = doRegister(s + "_potion_1", register);
          this.potionIconOverlay = doRegister(s + "_potion_2", register);
     }
     
     
     
     
     @Override
     public int getMaxItemUseDuration (ItemStack stack)
     {
          return 72000;
     }
     
     
     
     
     @Override
     public ItemStack onEaten (ItemStack stack, World world, EntityPlayer player)
     {
          return stack;
     }
     
     
     
     
     @Override
     public void onPlayerStoppedUsing (ItemStack itemStack, World world, EntityPlayer player, int extraInfo)
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
                    
                    if (item == ItemTW.crossBow)
                    {
                         stack = player.inventory.getStackInSlot(i);
                         
                         NBTTagCompound nbt = stack.getTagCompound();
                         
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
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean extraInfo)
     {
          List list1 = Item.potion.getEffects(stack);
          HashMultimap hashmultimap = HashMultimap.create();
          Iterator iterator;
          
          if (list1 != null && !list1.isEmpty())
          {
               iterator = list1.iterator();
               
               while (iterator.hasNext())
               {
                    PotionEffect potioneffect = (PotionEffect) iterator.next();
                    String s = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
                    Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                    Map map = potion.func_111186_k();
                    
                    if (map != null && map.size() > 0)
                    {
                         Iterator iterator1 = map.entrySet().iterator();
                         
                         while (iterator1.hasNext())
                         {
                              Entry entry = (Entry) iterator1.next();
                              AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();
                              AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.func_111183_a(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                              hashmultimap.put(((Attribute) entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
                         }
                    }
                    
                    if (potioneffect.getAmplifier() > 0)
                    {
                         s = s + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
                    }
                    
                    if (potioneffect.getDuration() > 20)
                    {
                         s = s + " (" + Potion.getDurationString(potioneffect) + ")";
                    }
                    
                    if (potion.isBadEffect())
                    {
                         list.add(EnumChatFormatting.RED + s);
                    }
                    else
                    {
                         list.add(EnumChatFormatting.GRAY + s);
                    }
               }
          }
          else
          {
               String s1 = StatCollector.translateToLocal("potion.empty").trim();
               list.add(EnumChatFormatting.GRAY + s1);
          }
          
          if (!hashmultimap.isEmpty())
          {
               list.add("");
               list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
               iterator = hashmultimap.entries().iterator();
               
               while (iterator.hasNext())
               {
                    Entry entry1 = (Entry) iterator.next();
                    AttributeModifier attributemodifier2 = (AttributeModifier) entry1.getValue();
                    double d0 = attributemodifier2.getAmount();
                    double d1;
                    
                    if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2)
                    {
                         d1 = attributemodifier2.getAmount();
                    }
                    else
                    {
                         d1 = attributemodifier2.getAmount() * 100.0D;
                    }
                    
                    if (d0 > 0.0D)
                    {
                         list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[]
                         { ItemStack.field_111284_a.format(d1), StatCollector.translateToLocal("attribute.name." + (String) entry1.getKey()) }));
                    }
                    else if (d0 < 0.0D)
                    {
                         d1 *= -1.0D;
                         list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[]
                         { ItemStack.field_111284_a.format(d1), StatCollector.translateToLocal("attribute.name." + (String) entry1.getKey()) }));
                    }
               }
          }
     }
     
     
     
     
     private boolean isPotionArrow (ItemStack stack)
     {
          if (stack.stackTagCompound == null)
          {
               stack.stackTagCompound = new NBTTagCompound();
          }
          
          return stack.stackTagCompound.hasKey("CustomPotionEffects");
     }
     
     
     
     
     @Override
     public int getPasses (ItemStack stack)
     {
          if (isPotionArrow(stack))
          {
               return 2;
          }
          
          return 1;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (ItemStack stack, int pass)
     {
          if (isPotionArrow(stack))
          {
               if (pass == 1)
               {
                    return potionIconOverlay;
               }
               
               return potionIcon;
          }
          
          return itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (ItemStack stack, int pass)
     {
          if (isPotionArrow(stack))
          {
               if (pass == 1)
               {
                    List<PotionEffect> effects = Item.potion.getEffects(stack);
                    
                    if (effects != null)
                    {
                         float[] r = new float[effects.size()];
                         float[] g = new float[effects.size()];
                         float[] b = new float[effects.size()];
                         
                         for (int i = 0; i < effects.size(); i++)
                         {
                              PotionEffect effect = effects.get(i);
                              float[] rgb = Assets.hexToRGB(Potion.potionTypes[effect.getPotionID()].getLiquidColor());
                              
                              r[i] = rgb[0];
                              g[i] = rgb[1];
                              b[i] = rgb[2];
                         }
                         
                         float finalR = Assets.average(r);
                         float finalG = Assets.average(g);
                         float finalB = Assets.average(b);
                         
                         Color color = new Color(finalR, finalG, finalB);
                         
                         return color.getRGB();
                    }
               }
               
               return 0xffffff;
          }
          
          return 0xffffff;
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (ItemStack stack, int pass)
     {
          if (isPotionArrow(stack))
          {
               return pass == 1;
          }
          
          return false;
     }
}