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
     
     
     
     
     public EntityBolt (World world)
     {
          super(world);
          this.renderDistanceWeight = 10.0D;
          this.setSize(0.5F, 0.5F);
     }
     
     
     
     
     public EntityBolt (World world, EntityLivingBase living, float par3, NBTTagList nbtTagList)
     {
          super(world);
          this.renderDistanceWeight = 10.0D;
          this.shootingEntity = living;
          
          if (living instanceof EntityPlayer)
          {
               this.canBePickedUp = 1;
          }
          
          this.setSize(0.5F, 0.5F);
          this.setLocationAndAngles(living.posX, living.posY + living.getEyeHeight(), living.posZ, living.rotationYaw, living.rotationPitch);
          this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
          this.posY -= 0.10000000149011612D;
          this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
          this.setPosition(this.posX, this.posY, this.posZ);
          this.yOffset = 0.0F;
          this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
          this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
          this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI);
          this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
          
          this.potionEffects = nbtTagList;
     }
     
     
     
     
     @Override
     protected void entityInit ()
     {
          this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
     }
     
     
     
     
     @Override
     public void setThrowableHeading (double par1, double par3, double par5, float par7, float par8)
     {
          float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
          par1 /= var9;
          par3 /= var9;
          par5 /= var9;
          par1 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
          par3 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
          par5 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
          par1 *= par7;
          par3 *= par7;
          par5 *= par7;
          this.motionX = par1;
          this.motionY = par3;
          this.motionZ = par5;
          float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
          this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
          this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI);
          this.ticksInGround = 0;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void setPositionAndRotation2 (double par1, double par3, double par5, float par7, float par8, int par9)
     {
          this.setPosition(par1, par3, par5);
          this.setRotation(par7, par8);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void setVelocity (double par1, double par3, double par5)
     {
          this.motionX = par1;
          this.motionY = par3;
          this.motionZ = par5;
          
          if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
          {
               float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
               this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
               this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI);
               this.prevRotationPitch = this.rotationPitch;
               this.prevRotationYaw = this.rotationYaw;
               this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
               this.ticksInGround = 0;
          }
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          super.onUpdate();
          
          if (Assets.isClient() && this.potionEffects != null && this.potionEffects.tagCount() > 0)
          {
               int amount = inGround ? 1 : 5;
               
               for (int i = 0; i < amount; i++)
               {
                    double xOffset = rand.nextDouble() - 0.5;
                    double yOffset = rand.nextDouble() - 0.5;
                    double zOffset = rand.nextDouble() - 0.5;
                    
                    EntityFX entityfx = Minecraft.getMinecraft().renderGlobal.doSpawnParticle("spell", this.posX + xOffset, this.posY + yOffset, this.posZ + zOffset, 0, 0, 0);
                    
                    if (entityfx != null)
                    {
                         List<PotionEffect> effects = new ArrayList<PotionEffect>();
                         
                         for (int i2 = 0; i2 < this.potionEffects.tagCount(); i2++)
                         {
                              PotionEffect effect = PotionEffect.readCustomPotionEffectFromNBT((NBTTagCompound) this.potionEffects.tagAt(i2));
                              effects.add(effect);
                         }
                         
                         float[] r = new float[effects.size()];
                         float[] g = new float[effects.size()];
                         float[] b = new float[effects.size()];
                         
                         for (int i3 = 0; i3 < effects.size(); i3++)
                         {
                              PotionEffect effect = effects.get(i3);
                              float[] rgb = Assets.hexToRGB(Potion.potionTypes[effect.getPotionID()].getLiquidColor());
                              
                              r[i3] = rgb[0];
                              g[i3] = rgb[1];
                              b[i3] = rgb[2];
                         }
                         
                         float finalR = Assets.average(r);
                         float finalG = Assets.average(g);
                         float finalB = Assets.average(b);
                         
                         entityfx.setRBGColorF(finalR, finalG, finalB);
                    }
               }
          }
          
          if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
          {
               float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
               this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
               this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, var1) * 180.0D / Math.PI);
          }
          
          int var16 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
          
          if (var16 > 0)
          {
               Block.blocksList[var16].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
               AxisAlignedBB var2 = Block.blocksList[var16].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
               
               if (var2 != null && var2.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
               {
                    this.inGround = true;
               }
          }
          
          if (this.boltShake > 0)
          {
               --this.boltShake;
          }
          
          if (this.inGround)
          {
               int var18 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
               int var19 = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
               
               if (var18 == this.inTile && var19 == this.inData)
               {
                    ++this.ticksInGround;
                    
                    if (this.ticksInGround == 1200)
                    {
                         this.setDead();
                    }
               }
               else
               {
                    this.inGround = false;
                    this.motionX *= this.rand.nextFloat() * 0.2F;
                    this.motionY *= this.rand.nextFloat() * 0.2F;
                    this.motionZ *= this.rand.nextFloat() * 0.2F;
                    this.ticksInGround = 0;
                    this.ticksInAir = 0;
               }
          }
          else
          {
               ++this.ticksInAir;
               Vec3 var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
               Vec3 var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
               MovingObjectPosition var4 = this.worldObj.rayTraceBlocks_do_do(var17, var3, false, true);
               var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
               var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
               
               if (var4 != null)
               {
                    var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
               }
               
               Entity var5 = null;
               List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
               double var7 = 0.0D;
               int var9;
               float var11;
               
               for (var9 = 0; var9 < var6.size(); ++var9)
               {
                    Entity var10 = (Entity) var6.get(var9);
                    
                    if (var10.canBeCollidedWith() && (var10 != this.shootingEntity || this.ticksInAir >= 5))
                    {
                         var11 = 0.3F;
                         AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
                         MovingObjectPosition var13 = var12.calculateIntercept(var17, var3);
                         
                         if (var13 != null)
                         {
                              double var14 = var17.distanceTo(var13.hitVec);
                              
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
                         var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                         int var23 = MathHelper.ceiling_double_int(var20 * this.damage);
                         
                         DamageSource var21 = null;
                         
                         if (this.shootingEntity == null)
                         {
                              var21 = DamageSource.causeThrownDamage(this, this);
                         }
                         else
                         {
                              var21 = DamageSource.causeThrownDamage(this, this.shootingEntity);
                         }
                         
                         if (this.isBurning() && !(var4.entityHit instanceof EntityEnderman))
                         {
                              var4.entityHit.setFire(5);
                         }
                         
                         if (this.hasFire && !(var4.entityHit instanceof EntityEnderman))
                         {
                              var4.entityHit.setFire(5);
                         }
                         
                         DamageSource source = new DamageSourceWeapon("tuxweapons:bolt", var4.entityHit, this.shootingEntity, null);
                         
                         this.setDead();
                         this.kill();
                         this.isDead = true;
                         
                         int i1 = MathHelper.ceiling_double_int((double) MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) * 1);
                         i1 += this.rand.nextInt(i1 / 2 + 2);
                         
                         if (var4.entityHit.attackEntityFrom(source, i1 - 1))
                         {
                              if (this.potionEffects != null && this.potionEffects.tagCount() > 0)
                              {
                                   for (int i = 0; i < this.potionEffects.tagCount(); i++)
                                   {
                                        PotionEffect effect = PotionEffect.readCustomPotionEffectFromNBT((NBTTagCompound) this.potionEffects.tagAt(i));
                                        ((EntityLivingBase) var4.entityHit).addPotionEffect(effect);
                                   }
                              }
                              
                              if (var4.entityHit instanceof EntityLiving)
                              {
                                   EntityLiving var24 = (EntityLiving) var4.entityHit;
                                   
                                   var24.setArrowCountInEntity(var24.getArrowCountInEntity() + 1);
                                   
                                   if (this.knockbackStrength > 0)
                                   {
                                        var26 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                        
                                        if (var26 > 0.0F)
                                        {
                                             var4.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / var26, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / var26);
                                        }
                                   }
                                   
                                   if (this.shootingEntity != null)
                                   {
                                        EnchantmentThorns.func_92096_a(this.shootingEntity, var24, this.rand);
                                   }
                                   
                                   if (this.shootingEntity != null && var4.entityHit != this.shootingEntity && var4.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                                   {
                                        ((EntityPlayerMP) this.shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
                                   }
                              }
                              
                              this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                              
                              if (!(var4.entityHit instanceof EntityEnderman))
                              {
                                   this.setDead();
                              }
                         }
                         else
                         {
                              this.motionX *= -0.10000000149011612D;
                              this.motionY *= -0.10000000149011612D;
                              this.motionZ *= -0.10000000149011612D;
                              this.rotationYaw += 180.0F;
                              this.prevRotationYaw += 180.0F;
                              this.ticksInAir = 0;
                         }
                    }
                    else
                    {
                         this.xTile = var4.blockX;
                         this.yTile = var4.blockY;
                         this.zTile = var4.blockZ;
                         this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                         this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                         this.motionX = (float) (var4.hitVec.xCoord - this.posX);
                         this.motionY = (float) (var4.hitVec.yCoord - this.posY);
                         this.motionZ = (float) (var4.hitVec.zCoord - this.posZ);
                         var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                         this.posX -= this.motionX / var20 * 0.05000000074505806D;
                         this.posY -= this.motionY / var20 * 0.05000000074505806D;
                         this.posZ -= this.motionZ / var20 * 0.05000000074505806D;
                         this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                         this.inGround = true;
                         this.boltShake = 7;
                         this.setIsCritical(false);
                         
                         if (this.inTile != 0)
                         {
                              Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                         }
                    }
               }
               
               for (var9 = 0; var9 < 4; ++var9)
               {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * var9 / 4.0D, this.posY + this.motionY * var9 / 4.0D, this.posZ + this.motionZ * var9 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
               }
               
               this.posX += this.motionX;
               this.posY += this.motionY;
               this.posZ += this.motionZ;
               var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
               this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
               
               for (this.rotationPitch = (float) (Math.atan2(this.motionY, var20) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
               {
                    ;
               }
               
               while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
               {
                    this.prevRotationPitch += 360.0F;
               }
               
               while (this.rotationYaw - this.prevRotationYaw < -180.0F)
               {
                    this.prevRotationYaw -= 360.0F;
               }
               
               while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
               {
                    this.prevRotationYaw += 360.0F;
               }
               
               this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
               this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
               float var22 = 0.99F;
               var11 = 0.05F;
               
               if (this.isInWater())
               {
                    for (int var25 = 0; var25 < 4; ++var25)
                    {
                         var26 = 0.25F;
                         this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var26, this.posY - this.motionY * var26, this.posZ - this.motionZ * var26, this.motionX, this.motionY, this.motionZ);
                    }
                    
                    var22 = 0.8F;
               }
               
               this.motionX *= var22;
               this.motionY *= var22;
               this.motionZ *= var22;
               this.motionY -= var11;
               this.setPosition(this.posX, this.posY, this.posZ);
               this.doBlockCollisions();
          }
     }
     
     
     
     
     @Override
     public void writeEntityToNBT (NBTTagCompound nbtTag)
     {
          nbtTag.setShort("xTile", (short) this.xTile);
          nbtTag.setShort("yTile", (short) this.yTile);
          nbtTag.setShort("zTile", (short) this.zTile);
          nbtTag.setByte("inTile", (byte) this.inTile);
          nbtTag.setByte("inData", (byte) this.inData);
          nbtTag.setByte("shake", (byte) this.boltShake);
          nbtTag.setByte("inGround", (byte) (this.inGround ? 1 : 0));
          nbtTag.setByte("pickup", (byte) this.canBePickedUp);
          nbtTag.setDouble("damage", this.damage);
          nbtTag.setTag("effects", potionEffects);
     }
     
     
     
     
     @Override
     public void readEntityFromNBT (NBTTagCompound nbtTag)
     {
          
          this.xTile = nbtTag.getShort("xTile");
          this.yTile = nbtTag.getShort("yTile");
          this.zTile = nbtTag.getShort("zTile");
          this.inTile = nbtTag.getByte("inTile") & 255;
          this.inData = nbtTag.getByte("inData") & 255;
          this.boltShake = nbtTag.getByte("shake") & 255;
          this.inGround = nbtTag.getByte("inGround") == 1;
          potionEffects = nbtTag.getTagList("effects");
          
          if (nbtTag.hasKey("damage"))
          {
               this.damage = nbtTag.getDouble("damage");
          }
          
          if (nbtTag.hasKey("pickup"))
          {
               this.canBePickedUp = nbtTag.getByte("pickup");
          }
          else if (nbtTag.hasKey("player"))
          {
               this.canBePickedUp = nbtTag.getBoolean("player") ? 1 : 0;
          }
     }
     
     
     
     
     @Override
     public void onCollideWithPlayer (EntityPlayer player)
     {
          if (this.inGround && this.boltShake <= 0)
          {
               this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
               player.onItemPickup(this, 1);
               this.setDead();
               this.kill();
               this.isDead = true;
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
          this.damage = 1;
     }
     
     
     
     
     public double getDamage ()
     {
          return this.damage;
     }
     
     
     
     
     public void setKnockbackStrength (int i)
     {
          this.knockbackStrength = i;
     }
     
     
     
     
     @Override
     public boolean canAttackWithItem ()
     {
          return true;
     }
     
     
     
     
     public void setIsCritical (boolean b)
     {
          byte var2 = this.dataWatcher.getWatchableObjectByte(16);
          
          if (b)
          {
               this.dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
          }
          else
          {
               this.dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
          }
     }
     
     
     
     
     public boolean getIsCritical ()
     {
          byte var1 = this.dataWatcher.getWatchableObjectByte(16);
          return (var1 & 1) != 0;
     }
}
