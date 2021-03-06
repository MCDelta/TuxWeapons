package mcdelta.tuxweapons.damage;

import static mcdelta.tuxweapons.damage.EnumDamageTypes.BASHER;
import static mcdelta.tuxweapons.damage.EnumDamageTypes.GOLDEN;
import static mcdelta.tuxweapons.damage.EnumDamageTypes.SLASHER;

import java.util.Random;

import mcdelta.core.logging.Logger;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.config.TWSettings;
import mcdelta.tuxweapons.item.ItemMace;
import mcdelta.tuxweapons.item.ItemShield;
import mcdelta.tuxweapons.item.ItemSpear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class DamageModifier
{
     Random rand = new Random();
     
     
     
     
     public static float getModifiedDamage (float f, final ItemStack stack, final Entity entity)
     {
          if (!TWSettings.DAMAGE_MODIFIER_ENABLE || stack == null)
          {
               return f;
          }
          
          final World world = entity.worldObj;
          final double x = entity.posX;
          final double y = entity.posY;
          final double z = entity.posZ;
          
          final int i1 = TWSettings.DAMAGE_MODIFIER_WEAK;
          final int i2 = TWSettings.DAMAGE_MODIFIER_RESIST;
          final int i3 = TWSettings.DAMAGE_MODIFIER_GOLD;
          
          if (EntityList.getEntityString(entity) != null)
          {
               if (BASHER.effc_item.contains(stack.getItem()) || SLASHER.effc_item.contains(stack.getItem()) || GOLDEN.effc_item.contains(stack.getItem()))
               {
                    if (BASHER.effc_entity.contains(EntityList.getEntityString(entity).toLowerCase()))
                    {
                         if (BASHER.effc_item.contains(stack.getItem()))
                         {
                              TuxWeapons.spawnParticle(1, world, x, y, z, entity, 0xAB3D3D, 1, 20, false);
                              f += i1;
                         }
                         
                         else
                         {
                              TuxWeapons.spawnParticle(2, world, x, y, z, entity, stack, 2, 2);
                              f -= i2;
                         }
                    }
                    
                    else if (SLASHER.effc_entity.contains(EntityList.getEntityString(entity).toLowerCase()))
                    {
                         if (SLASHER.effc_item.contains(stack.getItem()))
                         {
                              TuxWeapons.spawnParticle(1, world, x, y, z, entity, 0xAB3D3D, 1, 20, false);
                              f += i1;
                         }
                         
                         else
                         {
                              TuxWeapons.spawnParticle(2, world, x, y, z, entity, stack, 2, 2);
                              f -= i2;
                         }
                    }
                    
                    else if (GOLDEN.effc_entity.contains(EntityList.getEntityString(entity).toLowerCase()))
                    {
                         if (GOLDEN.effc_item.contains(stack.getItem()))
                         {
                              TuxWeapons.spawnParticle(1, world, x, y, z, entity, 0xAB3D3D, 1, 20, false);
                              f += i3;
                         }
                    }
               }
          }
          
          if (f <= 2)
          {
               f = 2;
          }
          
          return f;
     }
     
     
     
     
     @ForgeSubscribe
     public void livingHurt (final LivingHurtEvent event)
     {
          if (event.entityLiving instanceof EntityPlayer)
          {
               final EntityPlayer player = (EntityPlayer) event.entityLiving;
               
               if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemShield && player.getItemInUseCount() > 0 && event.source.getEntity() != null)
               {
                    event.setCanceled(true);
                    player.inventory.getCurrentItem().damageItem((int) event.ammount, player);
               }
          }
          
          if (event.source.getEntity() instanceof EntityLivingBase)
          {
               final EntityLivingBase living = (EntityLivingBase) event.source.getEntity();
               event.ammount = getModifiedDamage(event.ammount, living.getHeldItem(), event.entityLiving);
               
               if (living.getHeldItem() != null && living.getHeldItem().getItem() instanceof ItemMace)
               {
                    final float f = rand.nextInt(TWSettings.DAMAGE_MODIFIER_MACE + 1);
                    event.ammount += f;
                    
                    if (f >= TWSettings.DAMAGE_MODIFIER_MACE - 2)
                    {
                         TuxWeapons.spawnParticle(3, event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, event.entityLiving, 0xffffff, 1, 20, false);
                    }
               }
               
               if (living.getHeldItem() != null && living.getHeldItem().getItem() instanceof ItemSpear && !(event.source instanceof DamageSourceWeapon))
               {
                    event.entityLiving.attackEntityFrom(new DamageSourceWeapon("tuxweapons:spear", event.entity, living, living.getHeldItem()), 2);
               }
          }
          
          Logger.debug(event.ammount, EntityList.getEntityString(event.entityLiving) == null ? "nuuuull" : EntityList.getEntityString(event.entityLiving).toLowerCase(), event.source.damageType);
     }
}
