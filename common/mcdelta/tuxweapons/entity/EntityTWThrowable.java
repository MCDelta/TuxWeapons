package mcdelta.tuxweapons.entity;

import java.util.Iterator;
import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.item.ItemWeapon;
import mcdelta.tuxweapons.damage.DamageSourceWeapon;
import mcdelta.tuxweapons.item.ItemKnife;
import mcdelta.tuxweapons.network.PacketThrowablePickup;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTWThrowable extends Entity implements IProjectile, IEntityAdditionalSpawnData
{
     private int      xTile         = -1;
     private int      yTile         = -1;
     private int      zTile         = -1;
     private int      inTile        = 0;
     private int      inData        = 0;
     public boolean   inGround      = false;
     public int       canBePickedUp = 0;
     public int       arrowShake    = 0;
     public Entity    owner;
     public int       ownerID;
     private int      ticksInGround;
     private int      ticksInAir    = 0;
     public ItemStack stack;
     private boolean  hitEntity     = false;
     
     
     
     
     public EntityTWThrowable (final World world)
     {
          super(world);
          this.renderDistanceWeight = 10.0D;
          this.setSize(0.5F, 0.5F);
     }
     
     
     
     
     public EntityTWThrowable (final World world, final EntityLivingBase living, final float charge, final ItemStack item)
     {
          super(world);
          this.renderDistanceWeight = 10.0D;
          this.owner = living;
          this.ownerID = this.owner.entityId;
          
          if (this.owner instanceof EntityPlayer && !((EntityPlayer) this.owner).capabilities.isCreativeMode)
          {
               this.canBePickedUp = 1;
          }
          
          float f = 0;
          
          if (Assets.isClient())
          {
               f = 0.12F;
          }
          
          this.setSize(0.5F, 0.5F);
          this.setLocationAndAngles(this.owner.posX, this.owner.posY + this.owner.getEyeHeight() - f, this.owner.posZ, this.owner.rotationYaw, this.owner.rotationPitch);
          this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
          this.posY -= 0.10000000149011612D;
          this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
          this.setPosition(this.posX, this.posY, this.posZ);
          this.yOffset = 0.0F;
          this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
          this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
          this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI);
          this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, charge * 1.5F, 1.0F);
          
          this.stack = item;
     }
     
     
     
     
     @Override
     protected void entityInit ()
     {
          this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
     }
     
     
     
     
     @Override
     public void setThrowableHeading (double motX, double motY, double motZ, final float var7, final float var8)
     {
          final float var9 = MathHelper.sqrt_double(motX * motX + motY * motY + motZ * motZ);
          motX /= var9;
          motY /= var9;
          motZ /= var9;
          motX += this.rand.nextGaussian() * 0.007499999832361937D * var8;
          motY += this.rand.nextGaussian() * 0.007499999832361937D * var8;
          motZ += this.rand.nextGaussian() * 0.007499999832361937D * var8;
          motX *= var7;
          motY *= var7;
          motZ *= var7;
          this.motionX = motX;
          this.motionY = motY;
          this.motionZ = motZ;
          final float var10 = MathHelper.sqrt_double(motX * motX + motZ * motZ);
          this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(motX, motZ) * 180.0D / Math.PI);
          this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(motY, var10) * 180.0D / Math.PI);
          this.ticksInGround = 0;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void setPositionAndRotation (final double posX, final double posY, final double posZ, final float pitch, final float yaw)
     {
          this.setPosition(posX, posY, posZ);
          this.setRotation(pitch, yaw);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void setVelocity (final double x, final double y, final double z)
     {
          this.motionX = x;
          this.motionY = y;
          this.motionZ = z;
          
          if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
          {
               final float var7 = MathHelper.sqrt_double(x * x + z * z);
               this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
               this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, var7) * 180.0D / Math.PI);
               this.prevRotationPitch = this.rotationPitch;
               this.prevRotationYaw = this.rotationYaw;
               this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
               this.ticksInGround = 0;
          }
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          this.lastTickPosX = this.posX;
          this.lastTickPosY = this.posY;
          this.lastTickPosZ = this.posZ;
          
          super.onUpdate();
          
          if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
          {
               final float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
               this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180D / Math.PI);
               this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180D / Math.PI);
          }
          
          final int id = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
          
          if (id > 0)
          {
               Block.blocksList[id].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
               final AxisAlignedBB collisionBox = Block.blocksList[id].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
               
               if (collisionBox != null && collisionBox.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
               {
                    this.inGround = true;
               }
          }
          
          if (this.arrowShake > 0)
          {
               --this.arrowShake;
          }
          
          if (this.inGround)
          {
               final int blockID = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
               final int meta = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
               
               if (blockID == this.inTile && meta == this.inData)
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
               MovingObjectPosition pos = this.worldObj.rayTraceBlocks_do_do(var17, var3, false, true);
               var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
               var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
               
               if (pos != null)
               {
                    var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord);
               }
               
               Entity entity = null;
               final List<Entity> targets = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
               
               double var7 = 0.0D;
               final Iterator<Entity> iter = targets.iterator();
               float range;
               
               while (iter.hasNext())
               {
                    final Entity target = iter.next();
                    
                    if (target.canBeCollidedWith() && this.ticksInAir >= 3)
                    {
                         range = 0.3F;
                         final AxisAlignedBB var12 = target.boundingBox.expand(range, range, range);
                         final MovingObjectPosition pos2 = var12.calculateIntercept(var17, var3);
                         
                         if (pos2 != null)
                         {
                              final double distance = var17.distanceTo(pos2.hitVec);
                              
                              if (distance < var7 || var7 == 0.0D)
                              {
                                   entity = target;
                                   var7 = distance;
                              }
                         }
                    }
               }
               
               if (entity != null)
               {
                    pos = new MovingObjectPosition(entity);
               }
               
               float var20;
               
               if (pos != null)
               {
                    if (pos.entityHit != null)
                    {
                         var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                         
                         DamageSource source = null;
                         
                         if (this.owner == null)
                         {
                              source = new DamageSourceWeapon("tuxweapons:throwable", pos.entityHit, null, this.stack);
                         }
                         
                         else
                         {
                              source = new DamageSourceWeapon("tuxweapons:throwable", pos.entityHit, this.owner, this.stack);
                         }
                         
                         if (pos.entityHit instanceof EntityLivingBase && !this.hitEntity)
                         {
                              if (this.ticksInAir >= 3 && this.owner != null && pos.entityHit != this.owner)
                              {
                                   if (this.isBurning())
                                   {
                                        pos.entityHit.setFire(5);
                                   }
                                   
                                   this.worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F - 0.0F));
                                   
                                   this.motionX *= -0.10000000149011612D;
                                   this.motionY *= -0.10000000149011612D;
                                   this.motionZ *= -0.10000000149011612D;
                                   this.rotationYaw += 180.0F;
                                   this.prevRotationYaw += 180.0F;
                                   
                                   float damageDealt = 0;
                                   
                                   if (this.stack != null && this.stack.getItem() instanceof ItemWeapon)
                                   {
                                        damageDealt = ((ItemWeapon) this.stack.getItem()).itemMaterial.getDamageVsEntity() + 5;
                                   }
                                   
                                   else if (this.stack != null && this.stack.getItem() instanceof ItemKnife)
                                   {
                                        damageDealt = ((ItemKnife) this.stack.getItem()).itemMaterial.getDamageVsEntity() + 2;
                                   }
                                   
                                   if (damageDealt != 0)
                                   {
                                        pos.entityHit.attackEntityFrom(source, damageDealt);
                                   }
                                   
                                   this.hitEntity = true;
                              }
                         }
                    }
                    else
                    {
                         this.xTile = pos.blockX;
                         this.yTile = pos.blockY;
                         this.zTile = pos.blockZ;
                         this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                         this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                         this.motionX = (float) (pos.hitVec.xCoord - this.posX);
                         this.motionY = (float) (pos.hitVec.yCoord - this.posY);
                         this.motionZ = (float) (pos.hitVec.zCoord - this.posZ);
                         var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                         this.posX -= this.motionX / var20 * 0.05000000074505806D;
                         this.posY -= this.motionY / var20 * 0.05000000074505806D;
                         this.posZ -= this.motionZ / var20 * 0.05000000074505806D;
                         this.worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F - 0.0F));
                         this.inGround = true;
                         this.arrowShake = 7;
                         this.setIsCritical(false);
                    }
               }
               
               if (this.getIsCritical())
               {
                    for (int var21 = 0; var21 < 4; ++var21)
                    {
                         this.worldObj.spawnParticle("crit", this.posX + this.motionX * var21 / 4.0D, this.posY + this.motionY * var21 / 4.0D, this.posZ + this.motionZ * var21 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
                    }
               }
               
               this.posX += this.motionX;
               this.posY += this.motionY;
               this.posZ += this.motionZ;
               var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
               final float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
               
               for (this.rotationPitch = (float) (Math.atan2(this.motionY, f1) * 180D / 3.1415927410125732D); this.rotationPitch - this.prevRotationPitch < -180F; this.prevRotationPitch -= 360F)
               {
               }
               
               for (; this.rotationPitch - this.prevRotationPitch >= 180F; this.prevRotationPitch += 360F)
               {
               }
               
               for (; this.rotationYaw - this.prevRotationYaw < -180F; this.prevRotationYaw -= 360F)
               {
               }
               
               for (; this.rotationYaw - this.prevRotationYaw >= 180F; this.prevRotationYaw += 360F)
               {
               }
               
               this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
               this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
               float var23 = 0.99F;
               range = 0.05F;
               
               if (this.isInWater())
               {
                    for (int var26 = 0; var26 < 4; ++var26)
                    {
                         final float var27 = 0.25F;
                         this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var27, this.posY - this.motionY * var27, this.posZ - this.motionZ * var27, this.motionX, this.motionY, this.motionZ);
                    }
                    
                    var23 = 0.8F;
               }
               
               this.motionX *= var23;
               this.motionY *= var23;
               this.motionZ *= var23;
               this.motionY -= range;
               this.setPosition(this.posX, this.posY, this.posZ);
               this.doBlockCollisions();
          }
     }
     
     
     
     
     @Override
     public void writeEntityToNBT (final NBTTagCompound nbtTag)
     {
          nbtTag.setShort("xTile", (short) this.xTile);
          nbtTag.setShort("yTile", (short) this.yTile);
          nbtTag.setShort("zTile", (short) this.zTile);
          nbtTag.setByte("inTile", (byte) this.inTile);
          nbtTag.setByte("inData", (byte) this.inData);
          nbtTag.setByte("shake", (byte) this.arrowShake);
          nbtTag.setByte("inGround", (byte) (this.inGround ? 1 : 0));
          nbtTag.setByte("pickup", (byte) this.canBePickedUp);
          nbtTag.setByte("hitEntity", (byte) (this.hitEntity ? 1 : 0));
          
          final NBTTagCompound tag = new NBTTagCompound();
          this.stack.writeToNBT(tag);
          nbtTag.setCompoundTag("stack", tag);
          
          if (this.owner != null)
          {
               nbtTag.setInteger("owner", this.owner.entityId);
          }
     }
     
     
     
     
     @Override
     public void readEntityFromNBT (final NBTTagCompound nbtTag)
     {
          this.xTile = nbtTag.getShort("xTile");
          this.yTile = nbtTag.getShort("yTile");
          this.zTile = nbtTag.getShort("zTile");
          this.inTile = nbtTag.getByte("inTile") & 255;
          this.inData = nbtTag.getByte("inData") & 255;
          this.arrowShake = nbtTag.getByte("shake") & 255;
          this.inGround = nbtTag.getByte("inGround") == 1;
          this.canBePickedUp = nbtTag.getByte("pickup");
          this.hitEntity = nbtTag.getByte("hitEntity") == 1;
          
          this.stack = new ItemStack(0, 0, 0);
          this.stack.readFromNBT(nbtTag.getCompoundTag("stack"));
          
          if (nbtTag.hasKey("owner"))
          {
               this.ownerID = nbtTag.getInteger("owner");
               this.owner = this.worldObj.getEntityByID(this.ownerID);
          }
     }
     
     
     
     
     @Override
     public void onCollideWithPlayer (final EntityPlayer player)
     {
          if (Assets.isClient())
          {
               if (this.inGround && this.arrowShake <= 0)
               {
                    this.doCollide(player);
                    
                    player.onItemPickup(this, 1);
                    this.setDead();
                    this.kill();
                    this.isDead = true;
                    
                    this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    
                    PacketDispatcher.sendPacketToServer(Assets.populatePacket(new PacketThrowablePickup(player.entityId, this.entityId)));
               }
          }
     }
     
     
     
     
     public void doCollide (final EntityPlayer player)
     {
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
     
     
     
     
     @Override
     public boolean canAttackWithItem ()
     {
          return true;
     }
     
     
     
     
     public void setIsCritical (final boolean b)
     {
          
          final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
          
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
          final byte var1 = this.dataWatcher.getWatchableObjectByte(16);
          return (var1 & 1) != 0;
     }
     
     
     
     
     @Override
     public void writeSpawnData (final ByteArrayDataOutput data)
     {
          data.writeShort(this.stack.itemID);
          data.writeInt(this.ownerID);
     }
     
     
     
     
     @Override
     public void readSpawnData (final ByteArrayDataInput data)
     {
          this.stack = new ItemStack(data.readShort(), 1, 0);
          this.ownerID = data.readInt();
          this.owner = this.worldObj.getEntityByID(this.ownerID);
     }
}
