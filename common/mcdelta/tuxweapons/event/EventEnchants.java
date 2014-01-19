package mcdelta.tuxweapons.event;

import mcdelta.core.DeltaCore;
import mcdelta.tuxweapons.TWContent;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.config.TWSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class EventEnchants
{
     
     
     @ForgeSubscribe
     public void livingKilled (final LivingDeathEvent event)
     {
          final EntityLivingBase living = event.entityLiving;
          final World world = living.worldObj;
          final double x = living.posX;
          final double y = living.posY;
          final double z = living.posZ;
          
          if (event.source.getEntity() instanceof EntityPlayer)
          {
               final EntityPlayer attacker = (EntityPlayer) event.source.getEntity();
               final ItemStack stack = attacker.inventory.getCurrentItem();
               
               final int expLvl = EnchantmentHelper.getEnchantmentLevel(TWContent.expIncrease.effectId, stack);
               
               if (expLvl > 0 && world.getGameRules().getGameRuleBooleanValue("doMobLoot") && living instanceof EntityLiving)
               {
                    int i = ((EntityLiving) living).experienceValue * expLvl;
                    
                    while (i > 0)
                    {
                         final int j = EntityXPOrb.getXPSplit(i);
                         i -= j;
                         final EntityXPOrb xpOrb = new EntityXPOrb(world, x, y, z, j);
                         world.spawnEntityInWorld(xpOrb);
                    }
               }
          }
     }
     
     
     
     
     @ForgeSubscribe
     public void livingUpdate (final LivingUpdateEvent event)
     {
          if (event.entityLiving instanceof EntityPlayer && event.entityLiving.ticksExisted % 10 == 0)
          {
               final int swiftLvl = EnchantmentHelper.getEnchantmentLevel(TWContent.swift.effectId, ((EntityPlayer) event.entityLiving).getCurrentEquippedItem());
               
               if (swiftLvl > 0)
               {
                    event.entityLiving.addPotionEffect(new PotionEffect(3, 11, swiftLvl - 1, true));
               }
          }
     }
     
     
     
     
     @ForgeSubscribe
     public void livingHurt (final LivingHurtEvent event)
     {
          final EntityLivingBase living = event.entityLiving;
          
          if (EnchantmentHelper.getEnchantmentLevel(TWContent.hardened.effectId, living.getCurrentItemOrArmor(3)) > 0)
          {
               final Multimap<String, AttributeModifier> map = HashMultimap.create();
               map.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), new AttributeModifier(living.getPersistentID(), "Enchanted Armor Modifier", 100.0F, 0));
               
               living.getAttributeMap().applyAttributeModifiers(map);
          }
          
          else
          {
               final Multimap<String, AttributeModifier> map = HashMultimap.create();
               map.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), new AttributeModifier(living.getPersistentID(), "Enchanted Armor Modifier", 100.0F, 0));
               
               living.getAttributeMap().removeAttributeModifiers(map);
          }
          
          if (event.source.getEntity() instanceof EntityPlayer)
          {
               final EntityPlayer attacker = (EntityPlayer) event.source.getEntity();
               final ItemStack stack = attacker.inventory.getCurrentItem();
               
               final int venomLvl = EnchantmentHelper.getEnchantmentLevel(TWContent.venom.effectId, stack);
               
               if (venomLvl > 0)
               {
                    living.addPotionEffect(new PotionEffect(Potion.poison.id, 50 * venomLvl));
                    living.addPotionEffect(new PotionEffect(Potion.hunger.id, 200 * venomLvl));
               }
               
               final int strikeLvl = EnchantmentHelper.getEnchantmentLevel(TWContent.strike.effectId, stack);
               
               if (strikeLvl > 0)
               {
                    final float f = DeltaCore.rand.nextInt(TWSettings.DAMAGE_MODIFIER_STRIKE + strikeLvl + 1);
                    event.ammount += f;
                    
                    if (f >= TWSettings.DAMAGE_MODIFIER_STRIKE + strikeLvl - 1)
                    {
                         TuxWeapons.spawnParticle(3, event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, event.entityLiving, 0xffffff, 1, 20, false);
                    }
               }
          }
     }
     
     
     
     
     @ForgeSubscribe
     public void arrowNock (final ArrowNockEvent event)
     {
          if (event.entityPlayer instanceof EntityPlayer)
          {
               final EntityPlayer player = event.entityPlayer;
               final Item item = player.getCurrentEquippedItem().getItem();
               final ItemStack stack = player.getCurrentEquippedItem();
               final int drawbackLvl = EnchantmentHelper.getEnchantmentLevel(TWContent.drawback.effectId, player.getCurrentEquippedItem());
               
               if (drawbackLvl > 0)
               {
                    if (item == Item.bow)
                    {
                         event.setCanceled(true);
                         
                         if (player.capabilities.isCreativeMode || player.inventory.hasItem(Item.arrow.itemID))
                         {
                              player.setItemInUse(stack, item.getMaxItemUseDuration(stack) - drawbackLvl * 3);
                         }
                    }
                    
                    if (item == TWContent.crossBow)
                    {
                         event.setCanceled(true);
                         
                         if (stack.stackTagCompound == null)
                         {
                              stack.setTagCompound(new NBTTagCompound());
                              stack.stackTagCompound.setBoolean("Loaded", false);
                         }
                         
                         final NBTTagCompound tagCompound = stack.getTagCompound();
                         
                         if (tagCompound.getBoolean("Loaded") == true || player.capabilities.isCreativeMode)
                         {
                              player.setItemInUse(stack, item.getMaxItemUseDuration(stack) - drawbackLvl * 3);
                         }
                    }
                    
                    /**if (item == TWContent.fireChargeCannon)
                    {
                         event.setCanceled(true);
                         
                         boolean flag = player.capabilities.isCreativeMode;
                         
                         for (final Item item2 : TWContent.fireChargeCannon.ammo)
                         {
                              if (player.inventory.hasItem(item2.itemID))
                              {
                                   flag = true;
                              }
                         }
                         
                         if (flag)
                         {
                              player.setItemInUse(stack, item.getMaxItemUseDuration(stack) - drawbackLvl * 3);
                         }
                    }*/
               }
          }
     }
}
