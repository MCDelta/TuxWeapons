package mcdelta.tuxweapons.item;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ToolMaterial;
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
     
     public ItemHammer (ToolMaterial mat)
     {
          super("hammer", TuxWeapons.instance, mat, 3.0F);
          this.setMaxDamage((int) ((float) mat.getMaxUses() * 0.9F));
     }
     
     
     
     
     @Override
     public void onPlayerStoppedUsing (ItemStack stack, World world, EntityPlayer player, int i)
     {
          int charge = (this.getMaxItemUseDuration(stack) - i) / 2;
          int explosionSize = 6;
          
          if (charge > 1)
          {
               player.swingItem();
               stack.damageItem(explosionSize, player);
               
               if (stack.getItemDamage() <= 0 && !player.capabilities.isCreativeMode)
               {
                    Assets.clearCurrentItem(player);
               }
               
               double x = player.posX;
               double y = player.posY;
               double z = player.posZ;
               
               double d0;
               double d1;
               double d2;
               
               int i1 = MathHelper.floor_double(x - (double) explosionSize - 1.0D);
               int i2 = MathHelper.floor_double(y - (double) explosionSize - 1.0D);
               int i3 = MathHelper.floor_double(z - (double) explosionSize - 1.0D);
               int i4 = MathHelper.floor_double(x + (double) explosionSize + 1.0D);
               int i5 = MathHelper.floor_double(y + (double) explosionSize + 1.0D);
               int i6 = MathHelper.floor_double(z + (double) explosionSize + 1.0D);
               
               int knockBackLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack) + 1;
               
               List targets = player.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getAABBPool().getAABB((double) i1, (double) i2, (double) i3, (double) i4, (double) i5, (double) i6));
               
               Vec3 vec3 = world.getWorldVec3Pool().getVecFromPool(x, y, z);
               
               if (targets.contains(player))
               {
                    targets.remove(player);
               }
               
               for (int k2 = 0; k2 < targets.size(); ++k2)
               {
                    Entity entity = (Entity) targets.get(k2);
                    double d7 = entity.getDistance(x, y, z) / (double) explosionSize;
                    
                    if (d7 <= 1.0D)
                    {
                         d0 = entity.posX - x;
                         d1 = entity.posY + (double) entity.getEyeHeight() - y;
                         d2 = entity.posZ - z;
                         double d8 = (double) MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
                         
                         if (d8 != 0.0D)
                         {
                              d0 /= d8;
                              d1 /= d8;
                              d2 /= d8;
                              double d9 = (double) world.getBlockDensity(vec3, entity.boundingBox);
                              double d10 = (1.0D - d7) * d9;
                              
                              if (entity instanceof EntityLivingBase)
                              {
                                   float damage = toolMaterialDelta.getDamageVsEntity() + 1;
                                   
                                   boolean flag = entity.attackEntityFrom(new DamageSourceWeapon("tuxweapons:hammerSmash", entity, player, stack), damage);
                              }
                              
                              entity.addVelocity(0.0, 0.2, 0.0);
                              double d11 = EnchantmentProtection.func_92092_a(entity, d10);
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
     public ItemStack onEaten (ItemStack stack, World world, EntityPlayer player)
     {
          return stack;
     }
     
     
     
     
     @Override
     public EnumAction getItemUseAction (ItemStack stack)
     {
          return EnumAction.bow;
     }
}
