package mcdelta.tuxweapons.block.tileentity;

import java.util.ArrayList;
import java.util.List;

import mcdelta.tuxweapons.data.TWNBTTags;
import mcdelta.tuxweapons.potions.Potions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.brewing.PotionBrewedEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileBrewStand extends TileEntityBrewingStand implements ISidedInventory
{
     private String getPotionName (final ItemStack stack)
     {
          final List<PotionEffect> effects = Item.potion.getEffects(stack);
          String s;
          
          if (effects != null && !effects.isEmpty())
          {
               s = effects.get(0).getEffectName();
          }
          
          else
          {
               s = PotionHelper.func_77905_c(stack.getItemDamage());
          }
          
          return s;
     }
     
     
     
     
     private boolean canBrew ()
     {
          final ItemStack input = brewingItemStacks[3];
          
          if (input != null)
          {
               if (input.getItem().getPotionEffect() == null)
               {
                    return false;
               }
               
               for (int i = 0; i < 3; ++i)
               {
                    final ItemStack stack = brewingItemStacks[i];
                    
                    if (stack == null)
                    {
                         continue;
                    }
                    
                    if (stack.stackTagCompound == null)
                    {
                         stack.stackTagCompound = new NBTTagCompound();
                    }
                    
                    final List<PotionEffect> effects = Item.potion.getEffects(stack);
                    final String s = getPotionName(stack);
                    
                    if (stack != null)
                    {
                         if (Potions.itemToPotion.containsKey(input.getItem()) && s.contains("awkward"))
                         {
                              return true;
                         }
                         
                         if (Potions.modifiers.containsKey(input.getItem()))
                         {
                              final String modification = Potions.modifiers.get(input.getItem());
                              
                              if (effects != null)
                              {
                                   for (int l = 0; l < effects.size(); l++)
                                   {
                                        if (!(effects.get(l).duration >= 240000))
                                        {
                                             if (modification.contains("time") && !stack.stackTagCompound.hasKey(TWNBTTags.REDSTONE_USED))
                                             {
                                                  if (!Potion.potionTypes[effects.get(l).getPotionID()].isInstant())
                                                  {
                                                       return true;
                                                  }
                                             }
                                             
                                             if (modification.contains("upgrade") && effects.get(l).getAmplifier() == 0 && !Potions.nonUpgradable.contains(Potion.potionTypes[effects.get(l).getPotionID()]))
                                             {
                                                  return true;
                                             }
                                             
                                             if (modification.contains("infinite") && !Potion.potionTypes[effects.get(l).getPotionID()].isInstant() && !Potions.nonInifinite.contains(Potion.potionTypes[effects.get(l).getPotionID()]) && effects.get(l).duration == 24000)
                                             {
                                                  return true;
                                             }
                                        }
                                   }
                                   
                                   if (modification.contains("splash") && !ItemPotion.isSplash(stack.getItemDamage()))
                                   {
                                        return true;
                                   }
                              }
                              
                              if (modification.contains("negative"))
                              {
                                   if (effects != null)
                                   {
                                        for (int l = 0; l < effects.size(); l++)
                                        {
                                             if (!Potions.badPotions.contains(Potion.potionTypes[effects.get(l).getPotionID()]))
                                             {
                                                  return true;
                                             }
                                        }
                                   }
                                   
                                   else
                                   {
                                        return true;
                                   }
                              }
                         }
                         
                         if (input.getItem() instanceof ItemPotion && Item.potion.getEffects(stack) != null && input.getItem() != Item.glassBottle && effects != null)
                         {
                              return true;
                         }
                         
                         final int vanillaResult = getPotionResult(stack.getItemDamage(), input);
                         
                         if (!Potions.itemToPotion.containsKey(input.getItem()) && !Potions.modifiers.containsKey(input.getItem()) && Item.potion.getEffects(vanillaResult) == null && !(input.getItem() instanceof ItemPotion) && input.getItem() != Item.glassBottle)
                         {
                              return true;
                         }
                    }
               }
          }
          
          return false;
     }
     
     
     
     
     private void brewPotions ()
     {
          final ItemStack input = brewingItemStacks[3];
          
          if (canBrew())
          {
               for (int i = 0; i < 3; ++i)
               {
                    final ItemStack stack = brewingItemStacks[i];
                    
                    if (stack != null)
                    {
                         List<PotionEffect> effects = Item.potion.getEffects(stack);
                         
                         if (stack.stackTagCompound == null)
                         {
                              stack.stackTagCompound = new NBTTagCompound();
                         }
                         
                         //
                         //
                         //
                         //
                         //
                         //
                         //
                         //
                         //
                         //
                         // Ignore the white space =P
                         // or you WILL go crazy
                         // that is if this code hasn't already driven you off a cliff
                         // enjoy a look into my mind >=D
                         //
                         //              
                         //            
                         //          
                         //        
                         //      
                         //    
                         //  
                         //
                         //
                         
                         if (brewingItemStacks[3] != null && brewingItemStacks[3].getItem() instanceof ItemPotion && !(effects == null))
                         {
                              final NBTTagList tagList = new NBTTagList();
                              final List<PotionEffect> inputEffects = Item.potion.getEffects(brewingItemStacks[3]);
                              
                              for (int ii = 0; ii < effects.size(); ii++)
                              {
                                   PotionEffect result = effects.get(ii);
                                   boolean flag = true;
                                   
                                   for (int iii = 0; iii < inputEffects.size(); iii++)
                                   {
                                        final PotionEffect effect = inputEffects.get(iii);
                                        
                                        int newVal = (int) (effect.duration * 0.2);
                                        
                                        if (result.getPotionID() == effect.getPotionID())
                                        {
                                             newVal += result.duration;
                                             
                                             if (Potion.potionTypes[effect.getPotionID()].isInstant())
                                             {
                                                  newVal = 0;
                                             }
                                             
                                             if (newVal > 24000)
                                             {
                                                  newVal = 24000;
                                             }
                                             
                                             int amplifier = effect.getAmplifier();
                                             
                                             if (effect.getAmplifier() == result.getAmplifier() && effect.getAmplifier() == 1)
                                             {
                                                  amplifier = 2;
                                             }
                                             
                                             result = new PotionEffect(effect.getPotionID(), newVal, amplifier);
                                             tagList.appendTag(result.writeCustomPotionEffectToNBT(new NBTTagCompound()));
                                             inputEffects.remove(iii);
                                             flag = false;
                                        }
                                   }
                                   
                                   if (flag)
                                   {
                                        tagList.appendTag(result.writeCustomPotionEffectToNBT(new NBTTagCompound()));
                                   }
                              }
                              
                              if (tagList.tagCount() < 4)
                              {
                                   for (int ii = 0; ii < inputEffects.size(); ii++)
                                   {
                                        final PotionEffect effect = new PotionEffect(inputEffects.get(ii));
                                        
                                        int newVal = (int) (effect.duration * 0.2);
                                        
                                        if (Potion.potionTypes[effect.getPotionID()].isInstant())
                                        {
                                             newVal = 0;
                                        }
                                        
                                        tagList.appendTag(new PotionEffect(effect.getPotionID(), newVal, effect.getAmplifier()).writeCustomPotionEffectToNBT(new NBTTagCompound()));
                                   }
                              }
                              
                              if (brewingItemStacks[i].stackTagCompound == null)
                              {
                                   brewingItemStacks[i].stackTagCompound = new NBTTagCompound();
                              }
                              
                              brewingItemStacks[i].stackTagCompound.removeTag("CustomPotionEffects");
                              brewingItemStacks[i].stackTagCompound.setTag("CustomPotionEffects", tagList);
                         }
                         
                         //
                         //              
                         //            
                         //          
                         //        
                         //      
                         //    
                         //  
                         //
                         //
                         
                         if (stack != null && stack.getItem() instanceof ItemPotion)
                         {
                              final String s = getPotionName(stack);
                              boolean success = false;
                              int infinite = -1;
                              
                              if (Potions.itemToPotion.containsKey(input.getItem()) && s.contains("awkward"))
                              {
                                   if (effects == null)
                                   {
                                        effects = new ArrayList<PotionEffect>();
                                   }
                                   
                                   final Potion potion = Potions.itemToPotion.get(input.getItem());
                                   int time = (int) (Potions.time * potion.getEffectiveness());
                                   
                                   if (Potion.potionTypes[potion.id].isInstant())
                                   {
                                        time = 0;
                                   }
                                   
                                   effects.add(new PotionEffect(potion.id, time));
                                   success = true;
                              }
                              
                              //
                              //              
                              //            
                              //          
                              //        
                              //      
                              //    
                              //  
                              //
                              //
                              
                              if (Potions.modifiers.containsKey(input.getItem()))
                              {
                                   String modification = Potions.modifiers.get(input.getItem());
                                   
                                   if (effects != null)
                                   {
                                        if (!effects.isEmpty())
                                        {
                                             for (int l = 0; l < effects.size(); l++)
                                             {
                                                  if (modification.contains("time") && !stack.stackTagCompound.hasKey(TWNBTTags.REDSTONE_USED))
                                                  {
                                                       double time;
                                                       
                                                       modification = modification.replaceFirst("time", "");
                                                       
                                                       if (modification.contains("/"))
                                                       {
                                                            final String[] nums = modification.split("/");
                                                            time = Double.parseDouble(nums[0]) / Double.parseDouble(nums[1]);
                                                       }
                                                       
                                                       else
                                                       {
                                                            time = Double.parseDouble(modification);
                                                       }
                                                       
                                                       if (!Potion.potionTypes[effects.get(l).getPotionID()].isInstant())
                                                       {
                                                            final PotionEffect effect = new PotionEffect(effects.get(0));
                                                            effect.duration *= time;
                                                            effects.add(0, effect);
                                                       }
                                                       
                                                       stack.stackTagCompound.setBoolean(TWNBTTags.REDSTONE_USED, true);
                                                       success = true;
                                                  }
                                                  
                                                  //
                                                  //              
                                                  //            
                                                  //          
                                                  //        
                                                  //      
                                                  //    
                                                  //  
                                                  //
                                                  //
                                                  
                                                  if (modification.contains("upgrade") && effects.get(l).getAmplifier() == 0 && !Potions.nonUpgradable.contains(Potion.potionTypes[effects.get(l).getPotionID()]))
                                                  {
                                                       effects.set(l, new PotionEffect(effects.get(l).getPotionID(), effects.get(l).getDuration(), 1));
                                                       
                                                       if (!Potion.potionTypes[effects.get(l).getPotionID()].isInstant())
                                                       {
                                                            final PotionEffect effect = new PotionEffect(effects.get(l));
                                                            effect.duration *= (double) 1 / 2;
                                                            effects.set(l, effect);
                                                       }
                                                       
                                                       success = true;
                                                  }
                                                  
                                                  //
                                                  //              
                                                  //            
                                                  //          
                                                  //        
                                                  //      
                                                  //    
                                                  //  
                                                  //
                                                  //
                                                  
                                                  if (modification.contains("infinite") && !effects.get(l).getIsPotionDurationMax() && !Potions.nonInifinite.contains(Potion.potionTypes[effects.get(l).getPotionID()]) && !success && !(effects.get(l).duration >= 240000 && effects.get(l).duration == 24000))
                                                  {
                                                       infinite = l;
                                                       success = true;
                                                  }
                                             }
                                        }
                                        
                                        //
                                        //              
                                        //            
                                        //          
                                        //        
                                        //      
                                        //    
                                        //  
                                        //
                                        //
                                        
                                        if (modification.contains("splash") && !ItemPotion.isSplash(stack.getItemDamage()))
                                        {
                                             stack.setItemDamage(TWNBTTags.SPLASH_ID);
                                             
                                             for (int l = 0; l < effects.size(); l++)
                                             {
                                                  if (!Potion.potionTypes[effects.get(l).getPotionID()].isInstant())
                                                  {
                                                       final PotionEffect effect = new PotionEffect(effects.get(l));
                                                       effect.duration *= (double) 3 / 4;
                                                       effects.set(l, effect);
                                                  }
                                             }
                                             
                                             success = true;
                                        }
                                   }
                                   
                                   //
                                   //              
                                   //            
                                   //          
                                   //        
                                   //      
                                   //    
                                   //  
                                   //
                                   //
                                   
                                   if (modification.contains("negative"))
                                   {
                                        if (effects != null && !effects.isEmpty())
                                        {
                                             for (int l = 0; l < effects.size(); l++)
                                             {
                                                  int corruptedID = Potion.weakness.id;
                                                  
                                                  if (Potions.corruption.containsKey(effects.get(l).getPotionID()))
                                                  {
                                                       corruptedID = Potions.corruption.get(effects.get(l).getPotionID());
                                                  }
                                                  
                                                  effects.set(l, new PotionEffect(corruptedID, effects.get(l).getDuration(), effects.get(l).getAmplifier()));
                                             }
                                        }
                                        
                                        else
                                        {
                                             effects = new ArrayList<PotionEffect>();
                                             effects.add(new PotionEffect(Potion.weakness.id, Potions.time / 2));
                                             
                                             if (!ItemPotion.isSplash(stack.getItemDamage()))
                                             {
                                                  stack.setItemDamage(16);
                                             }
                                             else
                                             {
                                                  stack.setItemDamage(16400);
                                             }
                                        }
                                        
                                        success = true;
                                   }
                              }
                              
                              //
                              //              
                              //            
                              //          
                              //        
                              //      
                              //    
                              //  
                              //
                              //
                              // all done, see how bad was that? <3
                              //
                              //              
                              //            
                              //          
                              //        
                              //      
                              //    
                              //  
                              //
                              //
                              
                              if (success)
                              {
                                   final NBTTagList tagList = new NBTTagList();
                                   
                                   if (!effects.isEmpty())
                                   {
                                        for (int l = 0; l < effects.size(); l++)
                                        {
                                             final PotionEffect finalEffect = new PotionEffect(effects.get(l));
                                             
                                             if (finalEffect.duration > 24000 && !(finalEffect.duration >= 240000))
                                             {
                                                  finalEffect.duration = 24000;
                                             }
                                             
                                             if (infinite == l && !Potion.potionTypes[finalEffect.getPotionID()].isInstant())
                                             {
                                                  finalEffect.duration = 240000;
                                                  finalEffect.setPotionDurationMax(true);
                                             }
                                             
                                             tagList.appendTag(finalEffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
                                        }
                                   }
                                   
                                   stack.stackTagCompound.setTag(TWNBTTags.POTION_EFFECTS, tagList);
                              }
                              
                              final int vanillaResult = getPotionResult(stack.getItemDamage(), input);
                              
                              if (!Potions.itemToPotion.containsKey(input.getItem()) && !Potions.modifiers.containsKey(input.getItem()) && Item.potion.getEffects(vanillaResult) == null && !success)
                              {
                                   stack.setItemDamage(vanillaResult);
                              }
                         }
                    }
               }
               
               if (Item.itemsList[input.itemID].hasContainerItem())
               {
                    brewingItemStacks[3] = Item.itemsList[input.itemID].getContainerItemStack(brewingItemStacks[3]);
               }
               
               else if (input.getItem() instanceof ItemPotion)
               {
                    brewingItemStacks[3] = new ItemStack(Item.glassBottle);
               }
               
               else
               {
                    --brewingItemStacks[3].stackSize;
                    
                    if (brewingItemStacks[3].stackSize <= 0)
                    {
                         brewingItemStacks[3] = null;
                    }
               }
               
               MinecraftForge.EVENT_BUS.post(new PotionBrewedEvent(brewingItemStacks));
          }
     }
     
     private static final int[] input             = new int[]
                                                  { 3 };
     private static final int[] output            = new int[]
                                                  { 0, 1, 2 };
     public ItemStack[]         brewingItemStacks = new ItemStack[4];
     public int                 brewTime;
     private int                filledSlots;
     private int                ingredientID;
     private String             customName;
     
     
     
     
     @Override
     public String getInvName ()
     {
          return isInvNameLocalized() ? customName : "container.brewing";
     }
     
     
     
     
     @Override
     public boolean isInvNameLocalized ()
     {
          return customName != null && customName.length() > 0;
     }
     
     
     
     
     @Override
     public void func_94131_a (final String par1Str)
     {
          customName = par1Str;
     }
     
     
     
     
     @Override
     public int getSizeInventory ()
     {
          return brewingItemStacks.length;
     }
     
     
     
     
     @Override
     public void updateEntity ()
     {
          if (brewTime > 0)
          {
               --brewTime;
               
               if (brewTime == 0)
               {
                    brewPotions();
                    onInventoryChanged();
               }
               else if (!canBrew())
               {
                    brewTime = 0;
                    onInventoryChanged();
               }
               else if (ingredientID != brewingItemStacks[3].itemID)
               {
                    brewTime = 0;
                    onInventoryChanged();
               }
          }
          else if (canBrew())
          {
               // TODO default 400
               
               brewTime = 400;
               ingredientID = brewingItemStacks[3].itemID;
          }
          
          final int i = getFilledSlots();
          
          if (i != filledSlots)
          {
               filledSlots = i;
               worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i, 2);
          }
          
          super.updateEntity();
     }
     
     
     
     
     @Override
     public int getBrewTime ()
     {
          return brewTime;
     }
     
     
     
     
     private int getPotionResult (final int par1, final ItemStack stack)
     {
          return stack == null ? par1 : Item.itemsList[stack.itemID].isPotionIngredient() ? PotionHelper.applyIngredient(par1, Item.itemsList[stack.itemID].getPotionEffect()) : par1;
     }
     
     
     
     
     @Override
     public void readFromNBT (final NBTTagCompound nbtTag)
     {
          super.readFromNBT(nbtTag);
          final NBTTagList nbttaglist = nbtTag.getTagList("Items");
          brewingItemStacks = new ItemStack[getSizeInventory()];
          
          for (int i = 0; i < nbttaglist.tagCount(); ++i)
          {
               final NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
               final byte b0 = nbttagcompound1.getByte("Slot");
               
               if (b0 >= 0 && b0 < brewingItemStacks.length)
               {
                    brewingItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
               }
          }
          
          brewTime = nbtTag.getShort("BrewTime");
          
          if (nbtTag.hasKey("CustomName"))
          {
               customName = nbtTag.getString("CustomName");
          }
     }
     
     
     
     
     @Override
     public void writeToNBT (final NBTTagCompound nbtTag)
     {
          super.writeToNBT(nbtTag);
          nbtTag.setShort("BrewTime", (short) brewTime);
          final NBTTagList nbttaglist = new NBTTagList();
          
          for (int i = 0; i < brewingItemStacks.length; ++i)
          {
               if (brewingItemStacks[i] != null)
               {
                    final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    brewingItemStacks[i].writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
               }
          }
          
          nbtTag.setTag("Items", nbttaglist);
          
          if (isInvNameLocalized())
          {
               nbtTag.setString("CustomName", customName);
          }
     }
     
     
     
     
     @Override
     public ItemStack getStackInSlot (final int slot)
     {
          return slot >= 0 && slot < brewingItemStacks.length ? brewingItemStacks[slot] : null;
     }
     
     
     
     
     @Override
     public ItemStack decrStackSize (final int slot, final int i)
     {
          if (slot >= 0 && slot < brewingItemStacks.length)
          {
               final ItemStack itemstack = brewingItemStacks[slot];
               brewingItemStacks[slot] = null;
               return itemstack;
          }
          else
          {
               return null;
          }
     }
     
     
     
     
     @Override
     public ItemStack getStackInSlotOnClosing (final int slot)
     {
          if (slot >= 0 && slot < brewingItemStacks.length)
          {
               final ItemStack itemstack = brewingItemStacks[slot];
               brewingItemStacks[slot] = null;
               return itemstack;
          }
          else
          {
               return null;
          }
     }
     
     
     
     
     @Override
     public void setInventorySlotContents (final int slot, final ItemStack stack)
     {
          if (slot >= 0 && slot < brewingItemStacks.length)
          {
               brewingItemStacks[slot] = stack;
          }
     }
     
     
     
     
     @Override
     public int getInventoryStackLimit ()
     {
          return 64;
     }
     
     
     
     
     @Override
     public boolean isUseableByPlayer (final EntityPlayer player)
     {
          return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
     }
     
     
     
     
     @Override
     public void openChest ()
     {
     }
     
     
     
     
     @Override
     public void closeChest ()
     {
     }
     
     
     
     
     @Override
     public boolean isItemValidForSlot (final int slot, final ItemStack stack)
     {
          return slot == 3 ? Item.itemsList[stack.itemID].isPotionIngredient() : stack.getItem() instanceof ItemPotion || stack.itemID == Item.glassBottle.itemID;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void setBrewTime (final int i)
     {
          brewTime = i;
     }
     
     
     
     
     @Override
     public int getFilledSlots ()
     {
          int i = 0;
          
          for (int j = 0; j < 3; ++j)
          {
               if (brewingItemStacks[j] != null)
               {
                    i |= 1 << j;
               }
          }
          
          return i;
     }
     
     
     
     
     @Override
     public int[] getAccessibleSlotsFromSide (final int slot)
     {
          return slot == 1 ? input : output;
     }
     
     
     
     
     @Override
     public boolean canInsertItem (final int slot, final ItemStack stack, final int side)
     {
          return isItemValidForSlot(slot, stack);
     }
     
     
     
     
     @Override
     public boolean canExtractItem (final int slot, final ItemStack stack, final int side)
     {
          return true;
     }
}
