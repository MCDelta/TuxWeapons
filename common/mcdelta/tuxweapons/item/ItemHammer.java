package mcdelta.tuxweapons.item;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ItemMaterial;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.damage.DamageSourceWeapon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemHammer extends ItemWeapon
{
     
     public ItemHammer (final ItemMaterial mat)
     {
          super("hammer", TuxWeapons.instance, mat, 4.0F);
          this.setMaxDamage((int) (mat.maxUses() * 0.9F));
     }
     
     
     
     
     @Override
     public void onPlayerStoppedUsing (final ItemStack stack, final World world, final EntityPlayer player, final int i)
     {
          final int charge = (this.getMaxItemUseDuration(stack) - i) / 2;
          final int explosionSize = 6;
          
          if (charge > 1)
          {
               player.swingItem();
               stack.damageItem(explosionSize, player);
               
               if (stack.getItemDamage() <= 0 && !player.capabilities.isCreativeMode)
               {
                    Assets.clearCurrentItem(player);
               }
               
               final double x = player.posX;
               final double y = player.posY;
               final double z = player.posZ;
               
               double d0;
               double d1;
               double d2;
               
               final int i1 = MathHelper.floor_double(x - explosionSize - 1.0D);
               final int i2 = MathHelper.floor_double(y - explosionSize - 1.0D);
               final int i3 = MathHelper.floor_double(z - explosionSize - 1.0D);
               final int i4 = MathHelper.floor_double(x + explosionSize + 1.0D);
               final int i5 = MathHelper.floor_double(y + explosionSize + 1.0D);
               final int i6 = MathHelper.floor_double(z + explosionSize + 1.0D);
               
               final int knockBackLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack) + 1;
               
               final List<Entity> targets = player.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getAABBPool().getAABB(i1, i2, i3, i4, i5, i6));
               
               final Vec3 vec3 = world.getWorldVec3Pool().getVecFromPool(x, y, z);
               
               if (targets.contains(player))
               {
                    targets.remove(player);
               }
               
               for (int k2 = 0; k2 < targets.size(); ++k2)
               {
                    final Entity entity = targets.get(k2);
                    final double d7 = entity.getDistance(x, y, z) / explosionSize;
                    
                    if (d7 <= 1.0D)
                    {
                         d0 = entity.posX - x;
                         d1 = entity.posY + entity.getEyeHeight() - y;
                         d2 = entity.posZ - z;
                         final double d8 = MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
                         
                         if (d8 != 0.0D)
                         {
                              d0 /= d8;
                              d1 /= d8;
                              d2 /= d8;
                              final double d9 = world.getBlockDensity(vec3, entity.boundingBox);
                              final double d10 = (1.0D - d7) * d9;
                              
                              if (entity instanceof EntityLivingBase)
                              {
                                   float damage = itemMaterial.getDamageVsEntity() + 1;
                                   
                                   entity.attackEntityFrom(new DamageSourceWeapon("tuxweapons:hammerSmash", entity, player, stack), damage);
                              }
                              
                              entity.addVelocity(0.0, 0.2, 0.0);
                              final double d11 = EnchantmentProtection.func_92092_a(entity, d10);
                              entity.motionX += d0 * d11 * knockBackLvl;
                              entity.motionY += d1 * d11 * (knockBackLvl * 0.5);
                              entity.motionZ += d2 * d11 * knockBackLvl;
                         }
                    }
               }
               
               int id = world.getBlockId((int) x, (int) y - 2, (int) z);
               
               if (id == 0)
               {
                    id = 1;
               }
               
               if (Assets.isClient())
               {
                    TuxWeapons.spawnParticle(0, world, x, y - 1, z, 4, id);
               }
               
               player.worldObj.playSoundEffect(x, y, z, "random.explode", 0.2F, (0.5F + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
          }
     }
     
     
     
     
     @Override
     public ItemStack onEaten (final ItemStack stack, final World world, final EntityPlayer player)
     {
          return stack;
     }
     
     
     
     
     @Override
     public EnumAction getItemUseAction (final ItemStack stack)
     {
          return EnumAction.bow;
     }
}
