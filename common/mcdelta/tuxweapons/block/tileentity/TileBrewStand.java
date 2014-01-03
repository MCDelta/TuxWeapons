package mcdelta.tuxweapons.block.tileentity;

import java.util.ArrayList;
import java.util.List;

import mcdelta.tuxweapons.TWNBTTags;
import mcdelta.tuxweapons.specials.potions.Potions;
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
     private String getPotionName (ItemStack stack)
     {
          List<PotionEffect> effects = Item.potion.getEffects(stack);
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
          ItemStack input = this.brewingItemStacks[3];
          
          if (input != null)
          {
               if (input.getItem().getPotionEffect() == null)
               {
                    return false;
               }
               
               for (int i = 0; i < 3; ++i)
               {
                    ItemStack stack = this.brewingItemStacks[i];
                    
                    if (stack == null)
                    {
                         continue;
                    }
                    
                    if (stack.stackTagCompound == null)
                    {
                         stack.stackTagCompound = new NBTTagCompound();
                    }
                    
                    List<PotionEffect> effects = Item.potion.getEffects(stack);
                    String s = getPotionName(stack);
                    
                    if (stack != null)
                    {
                         if (Potions.itemToPotion.containsKey(input.getItem()) && s.contains("awkward"))
                         {
                              return true;
                         }
                         
                         if (Potions.modifiers.containsKey(input.getItem()))
                         {
                              String modification = Potions.modifiers.get(input.getItem());
                              
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
                                   
                                   if (modification.contains("splash") && !Item.potion.isSplash(stack.getItemDamage()))
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
                         
                         if ((input.getItem() instanceof ItemPotion) && (Item.potion.getEffects(stack) != null) && (input.getItem() != Item.glassBottle) && effects != null)
                         {
                              return true;
                         }
                         
                         int vanillaResult = this.getPotionResult(stack.getItemDamage(), input);
                         
                         if (!Potions.itemToPotion.containsKey(input.getItem()) && !Potions.modifiers.containsKey(input.getItem()) && Item.potion.getEffects(vanillaResult) == null && !(input.getItem() instanceof ItemPotion) && (input.getItem() != Item.glassBottle))
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
          ItemStack input = this.brewingItemStacks[3];
          
          if (this.canBrew())
          {
               for (int i = 0; i < 3; ++i)
               {
                    ItemStack stack = this.brewingItemStacks[i];
                    
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
                         
                         if (this.brewingItemStacks[3] != null && this.brewingItemStacks[3].getItem() instanceof ItemPotion && !(effects == null))
                         {
                              NBTTagList tagList = new NBTTagList();
                              List<PotionEffect> inputEffects = Item.potion.getEffects(this.brewingItemStacks[3]);
                              
                              for (int ii = 0; ii < effects.size(); ii++)
                              {
                                   PotionEffect result = effects.get(ii);
                                   boolean flag = true;
                                   
                                   for (int iii = 0; iii < inputEffects.size(); iii++)
                                   {
                                        PotionEffect effect = inputEffects.get(iii);
                                        
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
                                        PotionEffect effect = new PotionEffect(inputEffects.get(ii));
                                        
                                        int newVal = (int) (effect.duration * 0.2);
                                        
                                        if (Potion.potionTypes[effect.getPotionID()].isInstant())
                                        {
                                             newVal = 0;
                                        }
                                        
                                        tagList.appendTag(new PotionEffect(effect.getPotionID(), newVal, effect.getAmplifier()).writeCustomPotionEffectToNBT(new NBTTagCompound()));
                                   }
                              }
                              
                              if (this.brewingItemStacks[i].stackTagCompound == null)
                              {
                                   this.brewingItemStacks[i].stackTagCompound = new NBTTagCompound();
                              }
                              
                              this.brewingItemStacks[i].stackTagCompound.removeTag("CustomPotionEffects");
                              this.brewingItemStacks[i].stackTagCompound.setTag("CustomPotionEffects", tagList);
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
                              String s = getPotionName(stack);
                              boolean success = false;
                              int infinite = -1;
                              
                              if (Potions.itemToPotion.containsKey(input.getItem()) && s.contains("awkward"))
                              {
                                   if (effects == null)
                                   {
                                        effects = new ArrayList();
                                   }
                                   
                                   Potion potion = Potions.itemToPotion.get(input.getItem());
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
                                                            String[] nums = modification.split("/");
                                                            time = Double.parseDouble(nums[0]) / Double.parseDouble(nums[1]);
                                                       }
                                                       
                                                       else
                                                       {
                                                            time = Double.parseDouble(modification);
                                                       }
                                                       
                                                       if (!Potion.potionTypes[effects.get(l).getPotionID()].isInstant())
                                                       {
                                                            PotionEffect effect = new PotionEffect(effects.get(0));
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
                                                            PotionEffect effect = new PotionEffect(effects.get(l));
                                                            effect.duration *= ((double) 1 / 2);
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
                                        
                                        if (modification.contains("splash") && !Item.potion.isSplash(stack.getItemDamage()))
                                        {
                                             stack.setItemDamage(TWNBTTags.SPLASH_ID);
                                             
                                             for (int l = 0; l < effects.size(); l++)
                                             {
                                                  if (!Potion.potionTypes[effects.get(l).getPotionID()].isInstant())
                                                  {
                                                       PotionEffect effect = new PotionEffect(effects.get(l));
                                                       effect.duration *= ((double) 3 / 4);
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
                                             effects = new ArrayList();
                                             effects.add(new PotionEffect(Potion.weakness.id, Potions.time / 2));
                                             
                                             if (!Item.potion.isSplash(stack.getItemDamage()))
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
                                   NBTTagList tagList = new NBTTagList();
                                   
                                   if (!effects.isEmpty())
                                   {
                                        for (int l = 0; l < effects.size(); l++)
                                        {
                                             PotionEffect finalEffect = new PotionEffect(effects.get(l));
                                             
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
                              
                              int vanillaResult = this.getPotionResult(stack.getItemDamage(), input);
                              
                              if (!Potions.itemToPotion.containsKey(input.getItem()) && !Potions.modifiers.containsKey(input.getItem()) && Item.potion.getEffects(vanillaResult) == null && !success)
                              {
                                   stack.setItemDamage(vanillaResult);
                              }
                         }
                    }
               }
               
               if (Item.itemsList[input.itemID].hasContainerItem())
               {
                    this.brewingItemStacks[3] = Item.itemsList[input.itemID].getContainerItemStack(brewingItemStacks[3]);
               }
               
               else if (input.getItem() instanceof ItemPotion)
               {
                    this.brewingItemStacks[3] = new ItemStack(Item.glassBottle);
               }
               
               else
               {
                    --this.brewingItemStacks[3].stackSize;
                    
                    if (this.brewingItemStacks[3].stackSize <= 0)
                    {
                         this.brewingItemStacks[3] = null;
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
     
     
     
     
     public String getInvName ()
     {
          return this.isInvNameLocalized() ? this.customName : "container.brewing";
     }
     
     
     
     
     public boolean isInvNameLocalized ()
     {
          return this.customName != null && this.customName.length() > 0;
     }
     
     
     
     
     public void func_94131_a (String par1Str)
     {
          this.customName = par1Str;
     }
     
     
     
     
     public int getSizeInventory ()
     {
          return this.brewingItemStacks.length;
     }
     
     
     
     
     public void updateEntity ()
     {
          if (this.brewTime > 0)
          {
               --this.brewTime;
               
               if (this.brewTime == 0)
               {
                    this.brewPotions();
                    this.onInventoryChanged();
               }
               else if (!this.canBrew())
               {
                    this.brewTime = 0;
                    this.onInventoryChanged();
               }
               else if (this.ingredientID != this.brewingItemStacks[3].itemID)
               {
                    this.brewTime = 0;
                    this.onInventoryChanged();
               }
          }
          else if (this.canBrew())
          {
               // TODO default 400
               
               this.brewTime = 400;
               this.ingredientID = this.brewingItemStacks[3].itemID;
          }
          
          int i = this.getFilledSlots();
          
          if (i != this.filledSlots)
          {
               this.filledSlots = i;
               this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, i, 2);
          }
          
          super.updateEntity();
     }
     
     
     
     
     public int getBrewTime ()
     {
          return this.brewTime;
     }
     
     
     
     
     private int getPotionResult (int par1, ItemStack stack)
     {
          return stack == null ? par1 : (Item.itemsList[stack.itemID].isPotionIngredient() ? PotionHelper.applyIngredient(par1, Item.itemsList[stack.itemID].getPotionEffect()) : par1);
     }
     
     
     
     
     public void readFromNBT (NBTTagCompound nbtTag)
     {
          super.readFromNBT(nbtTag);
          NBTTagList nbttaglist = nbtTag.getTagList("Items");
          this.brewingItemStacks = new ItemStack[this.getSizeInventory()];
          
          for (int i = 0; i < nbttaglist.tagCount(); ++i)
          {
               NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
               byte b0 = nbttagcompound1.getByte("Slot");
               
               if (b0 >= 0 && b0 < this.brewingItemStacks.length)
               {
                    this.brewingItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
               }
          }
          
          this.brewTime = nbtTag.getShort("BrewTime");
          
          if (nbtTag.hasKey("CustomName"))
          {
               this.customName = nbtTag.getString("CustomName");
          }
     }
     
     
     
     
     public void writeToNBT (NBTTagCompound nbtTag)
     {
          super.writeToNBT(nbtTag);
          nbtTag.setShort("BrewTime", (short) this.brewTime);
          NBTTagList nbttaglist = new NBTTagList();
          
          for (int i = 0; i < this.brewingItemStacks.length; ++i)
          {
               if (this.brewingItemStacks[i] != null)
               {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.brewingItemStacks[i].writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
               }
          }
          
          nbtTag.setTag("Items", nbttaglist);
          
          if (this.isInvNameLocalized())
          {
               nbtTag.setString("CustomName", this.customName);
          }
     }
     
     
     
     
     public ItemStack getStackInSlot (int slot)
     {
          return slot >= 0 && slot < this.brewingItemStacks.length ? this.brewingItemStacks[slot] : null;
     }
     
     
     
     
     public ItemStack decrStackSize (int slot, int i)
     {
          if (slot >= 0 && slot < this.brewingItemStacks.length)
          {
               ItemStack itemstack = this.brewingItemStacks[slot];
               this.brewingItemStacks[slot] = null;
               return itemstack;
          }
          else
          {
               return null;
          }
     }
     
     
     
     
     public ItemStack getStackInSlotOnClosing (int slot)
     {
          if (slot >= 0 && slot < this.brewingItemStacks.length)
          {
               ItemStack itemstack = this.brewingItemStacks[slot];
               this.brewingItemStacks[slot] = null;
               return itemstack;
          }
          else
          {
               return null;
          }
     }
     
     
     
     
     public void setInventorySlotContents (int slot, ItemStack stack)
     {
          if (slot >= 0 && slot < this.brewingItemStacks.length)
          {
               this.brewingItemStacks[slot] = stack;
          }
     }
     
     
     
     
     public int getInventoryStackLimit ()
     {
          return 64;
     }
     
     
     
     
     public boolean isUseableByPlayer (EntityPlayer player)
     {
          return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
     }
     
     
     
     
     public void openChest ()
     {
     }
     
     
     
     
     public void closeChest ()
     {
     }
     
     
     
     
     public boolean isItemValidForSlot (int slot, ItemStack stack)
     {
          return slot == 3 ? Item.itemsList[stack.itemID].isPotionIngredient() : stack.getItem() instanceof ItemPotion || stack.itemID == Item.glassBottle.itemID;
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void setBrewTime (int i)
     {
          this.brewTime = i;
     }
     
     
     
     
     public int getFilledSlots ()
     {
          int i = 0;
          
          for (int j = 0; j < 3; ++j)
          {
               if (this.brewingItemStacks[j] != null)
               {
                    i |= 1 << j;
               }
          }
          
          return i;
     }
     
     
     
     
     public int[] getAccessibleSlotsFromSide (int slot)
     {
          return slot == 1 ? input : output;
     }
     
     
     
     
     public boolean canInsertItem (int slot, ItemStack stack, int side)
     {
          return this.isItemValidForSlot(slot, stack);
     }
     
     
     
     
     public boolean canExtractItem (int slot, ItemStack stack, int side)
     {
          return true;
     }
}
