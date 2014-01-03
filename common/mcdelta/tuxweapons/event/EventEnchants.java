package mcdelta.tuxweapons.event;

import mcdelta.core.DeltaCore;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.config.TWSettings;
import mcdelta.tuxweapons.item.ItemTW;
import mcdelta.tuxweapons.specials.enchant.EnchantmentTW;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMagmaCube;
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
     public void entityKilled (LivingDeathEvent event)
     {
          if (event.source.getDamageType().equals("player"))
          {
               if (event.entityLiving instanceof EntityMagmaCube)
               {
                    double rng = DeltaCore.rand.nextDouble();
                    
                    if (rng < 0.2)
                    {
                         event.entityLiving.dropItem(ItemTW.magmaCore.itemID, 1);
                    }
               }
          }
     }
     
     
     
     
     @ForgeSubscribe
     public void livingKilled (LivingDeathEvent event)
     {
          EntityLivingBase living = (EntityLivingBase) event.entityLiving;
          World world = living.worldObj;
          double x = living.posX;
          double y = living.posY;
          double z = living.posZ;
          
          if (event.source.getEntity() instanceof EntityPlayer)
          {
               EntityPlayer attacker = (EntityPlayer) event.source.getEntity();
               ItemStack stack = attacker.inventory.getCurrentItem();
               
               int expLvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentTW.expIncrease.effectId, stack);
               
               if (expLvl > 0 && world.getGameRules().getGameRuleBooleanValue("doMobLoot") && living instanceof EntityLiving)
               {
                    int i = (((EntityLiving) living).experienceValue) * expLvl;
                    
                    while (i > 0)
                    {
                         int j = EntityXPOrb.getXPSplit(i);
                         i -= j;
                         EntityXPOrb xpOrb = new EntityXPOrb(world, x, y, z, j);
                         world.spawnEntityInWorld(xpOrb);
                    }
               }
          }
     }
     
     
     
     
     @ForgeSubscribe
     public void livingUpdate (LivingUpdateEvent event)
     {
          if (event.entityLiving instanceof EntityPlayer && event.entityLiving.ticksExisted % 10 == 0)
          {
               int swiftLvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentTW.swift.effectId, ((EntityPlayer) event.entityLiving).getCurrentEquippedItem());
               
               if (swiftLvl > 0)
               {
                    event.entityLiving.addPotionEffect(new PotionEffect(3, 11, swiftLvl - 1, true));
               }
          }
     }
     
     
     
     
     @ForgeSubscribe
     public void livingHurt (LivingHurtEvent event)
     {
          EntityLivingBase living = (EntityLivingBase) event.entityLiving;
          World world = living.worldObj;
          double x = living.posX;
          double y = living.posY;
          double z = living.posZ;
          
          if (EnchantmentHelper.getEnchantmentLevel(EnchantmentTW.hardened.effectId, living.getCurrentItemOrArmor(3)) > 0)
          {
               Multimap map = HashMultimap.create();
               map.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), new AttributeModifier(living.getPersistentID(), "Enchanted Armor Modifier", 100.0F, 0));
               
               living.getAttributeMap().applyAttributeModifiers(map);
          }
          
          else
          {
               Multimap map = HashMultimap.create();
               map.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), new AttributeModifier(living.getPersistentID(), "Enchanted Armor Modifier", 100.0F, 0));
               
               living.getAttributeMap().removeAttributeModifiers(map);
          }
          
          if (event.source.getEntity() instanceof EntityPlayer)
          {
               EntityPlayer attacker = (EntityPlayer) event.source.getEntity();
               ItemStack stack = attacker.inventory.getCurrentItem();
               
               int venomLvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentTW.venom.effectId, stack);
               
               if (venomLvl > 0)
               {
                    living.addPotionEffect(new PotionEffect(Potion.poison.id, 50 * venomLvl));
                    living.addPotionEffect(new PotionEffect(Potion.hunger.id, 200 * venomLvl));
               }
               
               int strikeLvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentTW.strike.effectId, stack);
               
               if (strikeLvl > 0)
               {
                    float f = DeltaCore.rand.nextInt(TWSettings.DAMAGE_MODIFIER_STRIKE + strikeLvl + 1);
                    event.ammount += f;
                    
                    if (f >= TWSettings.DAMAGE_MODIFIER_STRIKE + strikeLvl - 1)
                    {
                         TuxWeapons.spawnParticle(3, event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, event.entityLiving, 0xffffff, 1, 20, false);
                    }
               }
          }
     }
     
     
     
     
     @ForgeSubscribe
     public void arrowNock (ArrowNockEvent event)
     {
          if (event.entityPlayer instanceof EntityPlayer)
          {
               EntityPlayer player = event.entityPlayer;
               Item item = player.getCurrentEquippedItem().getItem();
               ItemStack stack = player.getCurrentEquippedItem();
               World world = player.worldObj;
               
               int drawbackLvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentTW.drawback.effectId, player.getCurrentEquippedItem());
               
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
                    
                    if (item == ItemTW.crossBow)
                    {
                         event.setCanceled(true);
                         
                         if (stack.stackTagCompound == null)
                         {
                              stack.setTagCompound(new NBTTagCompound());
                              stack.stackTagCompound.setBoolean("Loaded", false);
                         }
                         
                         NBTTagCompound tagCompound = stack.getTagCompound();
                         
                         if (tagCompound.getBoolean("Loaded") == true || player.capabilities.isCreativeMode)
                         {
                              player.setItemInUse(stack, item.getMaxItemUseDuration(stack) - drawbackLvl * 3);
                         }
                    }
                    
                    if (item == ItemTW.fireChargeCannon)
                    {
                         event.setCanceled(true);
                         
                         boolean flag = player.capabilities.isCreativeMode;
                         
                         for (Item item2 : ItemTW.fireChargeCannon.ammo)
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
                    }
               }
          }
     }
}
