package mcdelta.tuxweapons.entity;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.tuxweapons.damage.DamageSourceWeapon;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBolt extends Entity implements IProjectile
{
     
     private int        xTile         = -1;
     private int        yTile         = -1;
     private int        zTile         = -1;
     private int        inTile        = 0;
     private int        inData        = 0;
     private boolean    inGround      = false;
     public int         canBePickedUp = 1;
     public int         boltShake     = 0;
     public Entity      shootingEntity;
     private int        ticksInGround;
     private int        ticksInAir    = 0;
     private double     damage        = 0.015D;
     private int        knockbackStrength;
     private NBTTagList potionEffects;
     public boolean     hasFire;
     
     
     
     
     public EntityBolt (final World world)
     {
          super(world);
          renderDistanceWeight = 10.0D;
          setSize(0.5F, 0.5F);
     }
     
     
     
     
     public EntityBolt (final World world, final EntityLivingBase living, final float par3, final NBTTagList nbtTagList)
     {
          super(world);
          renderDistanceWeight = 10.0D;
          shootingEntity = living;
          
          if (living instanceof EntityPlayer)
          {
               canBePickedUp = 1;
          }
          
          setSize(0.5F, 0.5F);
          setLocationAndAngles(living.posX, living.posY + living.getEyeHeight(), living.posZ, living.rotationYaw, living.rotationPitch);
          posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
          posY -= 0.10000000149011612D;
          posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
          setPosition(posX, posY, posZ);
          yOffset = 0.0F;
          motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
          motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
          motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
          setThrowableHeading(motionX, motionY, motionZ, par3 * 1.5F, 1.0F);
          
          potionEffects = nbtTagList;
     }
     
     
     
     
     @Override
     protected void entityInit ()
     {
          dataWatcher.addObject(16, Byte.valueOf((byte) 0));
     }
     
     
     
     
     @Override
     public void setThrowableHeading (double par1, double par3, double par5, final float par7, final float par8)
     {
          final float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
          par1 /= var9;
          par3 /= var9;
          par5 /= var9;
          par1 += rand.nextGaussian() * 0.007499999832361937D * par8;
          par3 += rand.nextGaussian() * 0.007499999832361937D * par8;
          par5 += rand.nextGaussian() * 0.007499999832361937D * par8;
          par1 *= par7;
          par3 *= par7;
          par5 *= par7;
          motionX = par1;
          motionY = par3;
          motionZ = par5;
          final float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
          prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
          prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI);
          ticksInGround = 0;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void setPositionAndRotation2 (final double par1, final double par3, final double par5, final float par7, final float par8, final int par9)
     {
          setPosition(par1, par3, par5);
          setRotation(par7, par8);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void setVelocity (final double par1, final double par3, final double par5)
     {
          motionX = par1;
          motionY = par3;
          motionZ = par5;
          
          if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
          {
               final float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
               prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
               prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI);
               prevRotationPitch = rotationPitch;
               prevRotationYaw = rotationYaw;
               setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
               ticksInGround = 0;
          }
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          super.onUpdate();
          
          if (Assets.isClient() && potionEffects != null && potionEffects.tagCount() > 0)
          {
               final int amount = inGround ? 1 : 5;
               
               for (int i = 0; i < amount; i++)
               {
                    final double xOffset = rand.nextDouble() - 0.5;
                    final double yOffset = rand.nextDouble() - 0.5;
                    final double zOffset = rand.nextDouble() - 0.5;
                    
                    final EntityFX entityfx = Minecraft.getMinecraft().renderGlobal.doSpawnParticle("spell", posX + xOffset, posY + yOffset, posZ + zOffset, 0, 0, 0);
                    
                    if (entityfx != null)
                    {
                         final List<PotionEffect> effects = new ArrayList<PotionEffect>();
                         
                         for (int i2 = 0; i2 < potionEffects.tagCount(); i2++)
                         {
                              final PotionEffect effect = PotionEffect.readCustomPotionEffectFromNBT((NBTTagCompound) potionEffects.tagAt(i2));
                              effects.add(effect);
                         }
                         
                         final float[] r = new float[effects.size()];
                         final float[] g = new float[effects.size()];
                         final float[] b = new float[effects.size()];
                         
                         for (int i3 = 0; i3 < effects.size(); i3++)
                         {
                              final PotionEffect effect = effects.get(i3);
                              final float[] rgb = Assets.hexToRGB(Potion.potionTypes[effect.getPotionID()].getLiquidColor());
                              
                              r[i3] = rgb[0];
                              g[i3] = rgb[1];
                              b[i3] = rgb[2];
                         }
                         
                         final float finalR = Assets.average(r);
                         final float finalG = Assets.average(g);
                         final float finalB = Assets.average(b);
                         
                         entityfx.setRBGColorF(finalR, finalG, finalB);
                    }
               }
          }
          
          if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
          {
               final float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
               prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
               prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI);
          }
          
          final int var16 = worldObj.getBlockId(xTile, yTile, zTile);
          
          if (var16 > 0)
          {
               Block.blocksList[var16].setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
               final AxisAlignedBB var2 = Block.blocksList[var16].getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
               
               if (var2 != null && var2.isVecInside(worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ)))
               {
                    inGround = true;
               }
          }
          
          if (boltShake > 0)
          {
               --boltShake;
          }
          
          if (inGround)
          {
               final int var18 = worldObj.getBlockId(xTile, yTile, zTile);
               final int var19 = worldObj.getBlockMetadata(xTile, yTile, zTile);
               
               if (var18 == inTile && var19 == inData)
               {
                    ++ticksInGround;
                    
                    if (ticksInGround == 1200)
                    {
                         setDead();
                    }
               }
               else
               {
                    inGround = false;
                    motionX *= rand.nextFloat() * 0.2F;
                    motionY *= rand.nextFloat() * 0.2F;
                    motionZ *= rand.nextFloat() * 0.2F;
                    ticksInGround = 0;
                    ticksInAir = 0;
               }
          }
          else
          {
               ++ticksInAir;
               Vec3 var17 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
               Vec3 var3 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
               MovingObjectPosition var4 = worldObj.rayTraceBlocks_do_do(var17, var3, false, true);
               var17 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
               var3 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
               
               if (var4 != null)
               {
                    var3 = worldObj.getWorldVec3Pool().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
               }
               
               Entity var5 = null;
               final List<Entity> var6 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
               double var7 = 0.0D;
               int var9;
               float var11;
               
               for (var9 = 0; var9 < var6.size(); ++var9)
               {
                    final Entity var10 = var6.get(var9);
                    
                    if (var10.canBeCollidedWith() && (var10 != shootingEntity || ticksInAir >= 5))
                    {
                         var11 = 0.3F;
                         final AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
                         final MovingObjectPosition var13 = var12.calculateIntercept(var17, var3);
                         
                         if (var13 != null)
                         {
                              final double var14 = var17.distanceTo(var13.hitVec);
                              
                              if (var14 < var7 || var7 == 0.0D)
                              {
                                   var5 = var10;
                                   var7 = var14;
                              }
                         }
                    }
               }
               
               if (var5 != null)
               {
                    var4 = new MovingObjectPosition(var5);
               }
               
               float var20;
               float var26;
               
               if (var4 != null)
               {
                    if (var4.entityHit != null)
                    {
                         var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                         
                         if (shootingEntity == null)
                         {
                         }
                         else
                         {
                         }
                         
                         if (isBurning() && !(var4.entityHit instanceof EntityEnderman))
                         {
                              var4.entityHit.setFire(5);
                         }
                         
                         if (hasFire && !(var4.entityHit instanceof EntityEnderman))
                         {
                              var4.entityHit.setFire(5);
                         }
                         
                         final DamageSource source = new DamageSourceWeapon("tuxweapons:bolt", var4.entityHit, shootingEntity, null);
                         
                         setDead();
                         kill();
                         isDead = true;
                         
                         int i1 = MathHelper.ceiling_double_int((double) MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ) * 1);
                         i1 += rand.nextInt(i1 / 2 + 2);
                         
                         if (var4.entityHit.attackEntityFrom(source, i1 - 1))
                         {
                              if (potionEffects != null && potionEffects.tagCount() > 0)
                              {
                                   for (int i = 0; i < potionEffects.tagCount(); i++)
                                   {
                                        final PotionEffect effect = PotionEffect.readCustomPotionEffectFromNBT((NBTTagCompound) potionEffects.tagAt(i));
                                        ((EntityLivingBase) var4.entityHit).addPotionEffect(effect);
                                   }
                              }
                              
                              if (var4.entityHit instanceof EntityLiving)
                              {
                                   final EntityLiving var24 = (EntityLiving) var4.entityHit;
                                   
                                   var24.setArrowCountInEntity(var24.getArrowCountInEntity() + 1);
                                   
                                   if (knockbackStrength > 0)
                                   {
                                        var26 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
                                        
                                        if (var26 > 0.0F)
                                        {
                                             var4.entityHit.addVelocity(motionX * knockbackStrength * 0.6000000238418579D / var26, 0.1D, motionZ * knockbackStrength * 0.6000000238418579D / var26);
                                        }
                                   }
                                   
                                   if (shootingEntity != null)
                                   {
                                        EnchantmentThorns.func_92096_a(shootingEntity, var24, rand);
                                   }
                                   
                                   if (shootingEntity != null && var4.entityHit != shootingEntity && var4.entityHit instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP)
                                   {
                                        ((EntityPlayerMP) shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
                                   }
                              }
                              
                              playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                              
                              if (!(var4.entityHit instanceof EntityEnderman))
                              {
                                   setDead();
                              }
                         }
                         else
                         {
                              motionX *= -0.10000000149011612D;
                              motionY *= -0.10000000149011612D;
                              motionZ *= -0.10000000149011612D;
                              rotationYaw += 180.0F;
                              prevRotationYaw += 180.0F;
                              ticksInAir = 0;
                         }
                    }
                    else
                    {
                         xTile = var4.blockX;
                         yTile = var4.blockY;
                         zTile = var4.blockZ;
                         inTile = worldObj.getBlockId(xTile, yTile, zTile);
                         inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
                         motionX = (float) (var4.hitVec.xCoord - posX);
                         motionY = (float) (var4.hitVec.yCoord - posY);
                         motionZ = (float) (var4.hitVec.zCoord - posZ);
                         var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                         posX -= motionX / var20 * 0.05000000074505806D;
                         posY -= motionY / var20 * 0.05000000074505806D;
                         posZ -= motionZ / var20 * 0.05000000074505806D;
                         playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                         inGround = true;
                         boltShake = 7;
                         setIsCritical(false);
                         
                         if (inTile != 0)
                         {
                              Block.blocksList[inTile].onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
                         }
                    }
               }
               
               for (var9 = 0; var9 < 4; ++var9)
               {
                    worldObj.spawnParticle("crit", posX + motionX * var9 / 4.0D, posY + motionY * var9 / 4.0D, posZ + motionZ * var9 / 4.0D, -motionX, -motionY + 0.2D, -motionZ);
               }
               
               posX += motionX;
               posY += motionY;
               posZ += motionZ;
               var20 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
               rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
               
               for (rotationPitch = (float) (Math.atan2(motionY, var20) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
               {
                    ;
               }
               
               while (rotationPitch - prevRotationPitch >= 180.0F)
               {
                    prevRotationPitch += 360.0F;
               }
               
               while (rotationYaw - prevRotationYaw < -180.0F)
               {
                    prevRotationYaw -= 360.0F;
               }
               
               while (rotationYaw - prevRotationYaw >= 180.0F)
               {
                    prevRotationYaw += 360.0F;
               }
               
               rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
               rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
               float var22 = 0.99F;
               var11 = 0.05F;
               
               if (isInWater())
               {
                    for (int var25 = 0; var25 < 4; ++var25)
                    {
                         var26 = 0.25F;
                         worldObj.spawnParticle("bubble", posX - motionX * var26, posY - motionY * var26, posZ - motionZ * var26, motionX, motionY, motionZ);
                    }
                    
                    var22 = 0.8F;
               }
               
               motionX *= var22;
               motionY *= var22;
               motionZ *= var22;
               motionY -= var11;
               setPosition(posX, posY, posZ);
               doBlockCollisions();
          }
     }
     
     
     
     
     @Override
     public void writeEntityToNBT (final NBTTagCompound nbtTag)
     {
          nbtTag.setShort("xTile", (short) xTile);
          nbtTag.setShort("yTile", (short) yTile);
          nbtTag.setShort("zTile", (short) zTile);
          nbtTag.setByte("inTile", (byte) inTile);
          nbtTag.setByte("inData", (byte) inData);
          nbtTag.setByte("shake", (byte) boltShake);
          nbtTag.setByte("inGround", (byte) (inGround ? 1 : 0));
          nbtTag.setByte("pickup", (byte) canBePickedUp);
          nbtTag.setDouble("damage", damage);
          nbtTag.setTag("effects", potionEffects);
     }
     
     
     
     
     @Override
     public void readEntityFromNBT (final NBTTagCompound nbtTag)
     {
          
          xTile = nbtTag.getShort("xTile");
          yTile = nbtTag.getShort("yTile");
          zTile = nbtTag.getShort("zTile");
          inTile = nbtTag.getByte("inTile") & 255;
          inData = nbtTag.getByte("inData") & 255;
          boltShake = nbtTag.getByte("shake") & 255;
          inGround = nbtTag.getByte("inGround") == 1;
          potionEffects = nbtTag.getTagList("effects");
          
          if (nbtTag.hasKey("damage"))
          {
               damage = nbtTag.getDouble("damage");
          }
          
          if (nbtTag.hasKey("pickup"))
          {
               canBePickedUp = nbtTag.getByte("pickup");
          }
          else if (nbtTag.hasKey("player"))
          {
               canBePickedUp = nbtTag.getBoolean("player") ? 1 : 0;
          }
     }
     
     
     
     
     @Override
     public void onCollideWithPlayer (final EntityPlayer player)
     {
          if (inGround && boltShake <= 0)
          {
               playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
               player.onItemPickup(this, 1);
               setDead();
               kill();
               isDead = true;
          }
     }
     
     
     
     
     @Override
     protected boolean canTriggerWalking ()
     {
          return false;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public float getShadowSize ()
     {
          return 0.0F;
     }
     
     
     
     
     public void setDamage ()
     {
          damage = 1;
     }
     
     
     
     
     public double getDamage ()
     {
          return damage;
     }
     
     
     
     
     public void setKnockbackStrength (final int i)
     {
          knockbackStrength = i;
     }
     
     
     
     
     @Override
     public boolean canAttackWithItem ()
     {
          return true;
     }
     
     
     
     
     public void setIsCritical (final boolean b)
     {
          final byte var2 = dataWatcher.getWatchableObjectByte(16);
          
          if (b)
          {
               dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
          }
          else
          {
               dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
          }
     }
     
     
     
     
     public boolean getIsCritical ()
     {
          final byte var1 = dataWatcher.getWatchableObjectByte(16);
          return (var1 & 1) != 0;
     }
}
