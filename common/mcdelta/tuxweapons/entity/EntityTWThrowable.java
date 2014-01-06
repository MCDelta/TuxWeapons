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
          renderDistanceWeight = 10.0D;
          setSize(0.5F, 0.5F);
     }
     
     
     
     
     public EntityTWThrowable (final World world, final EntityLivingBase living, final float charge, final ItemStack item)
     {
          super(world);
          renderDistanceWeight = 10.0D;
          owner = living;
          ownerID = owner.entityId;
          
          if (owner instanceof EntityPlayer && !((EntityPlayer) owner).capabilities.isCreativeMode)
          {
               canBePickedUp = 1;
          }
          
          float f = 0;
          
          if (Assets.isClient())
          {
               f = 0.12F;
          }
          
          setSize(0.5F, 0.5F);
          setLocationAndAngles(owner.posX, owner.posY + owner.getEyeHeight() - f, owner.posZ, owner.rotationYaw, owner.rotationPitch);
          posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
          posY -= 0.10000000149011612D;
          posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
          setPosition(posX, posY, posZ);
          yOffset = 0.0F;
          motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
          motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
          motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
          setThrowableHeading(motionX, motionY, motionZ, charge * 1.5F, 1.0F);
          
          stack = item;
     }
     
     
     
     
     @Override
     protected void entityInit ()
     {
          dataWatcher.addObject(16, Byte.valueOf((byte) 0));
     }
     
     
     
     
     @Override
     public void setThrowableHeading (double motX, double motY, double motZ, final float var7, final float var8)
     {
          final float var9 = MathHelper.sqrt_double(motX * motX + motY * motY + motZ * motZ);
          motX /= var9;
          motY /= var9;
          motZ /= var9;
          motX += rand.nextGaussian() * 0.007499999832361937D * var8;
          motY += rand.nextGaussian() * 0.007499999832361937D * var8;
          motZ += rand.nextGaussian() * 0.007499999832361937D * var8;
          motX *= var7;
          motY *= var7;
          motZ *= var7;
          motionX = motX;
          motionY = motY;
          motionZ = motZ;
          final float var10 = MathHelper.sqrt_double(motX * motX + motZ * motZ);
          prevRotationYaw = rotationYaw = (float) (Math.atan2(motX, motZ) * 180.0D / Math.PI);
          prevRotationPitch = rotationPitch = (float) (Math.atan2(motY, var10) * 180.0D / Math.PI);
          ticksInGround = 0;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void setPositionAndRotation (final double posX, final double posY, final double posZ, final float pitch, final float yaw)
     {
          setPosition(posX, posY, posZ);
          setRotation(pitch, yaw);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void setVelocity (final double x, final double y, final double z)
     {
          motionX = x;
          motionY = y;
          motionZ = z;
          
          if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
          {
               final float var7 = MathHelper.sqrt_double(x * x + z * z);
               prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
               prevRotationPitch = rotationPitch = (float) (Math.atan2(y, var7) * 180.0D / Math.PI);
               prevRotationPitch = rotationPitch;
               prevRotationYaw = rotationYaw;
               setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
               ticksInGround = 0;
          }
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          lastTickPosX = posX;
          lastTickPosY = posY;
          lastTickPosZ = posZ;
          
          super.onUpdate();
          
          if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
          {
               final float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
               prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180D / Math.PI);
               prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, f) * 180D / Math.PI);
          }
          
          final int id = worldObj.getBlockId(xTile, yTile, zTile);
          
          if (id > 0)
          {
               Block.blocksList[id].setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
               final AxisAlignedBB collisionBox = Block.blocksList[id].getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
               
               if (collisionBox != null && collisionBox.isVecInside(worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ)))
               {
                    inGround = true;
               }
          }
          
          if (arrowShake > 0)
          {
               --arrowShake;
          }
          
          if (inGround)
          {
               final int blockID = worldObj.getBlockId(xTile, yTile, zTile);
               final int meta = worldObj.getBlockMetadata(xTile, yTile, zTile);
               
               if (blockID == inTile && meta == inData)
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
               MovingObjectPosition pos = worldObj.rayTraceBlocks_do_do(var17, var3, false, true);
               var17 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
               var3 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
               
               if (pos != null)
               {
                    var3 = worldObj.getWorldVec3Pool().getVecFromPool(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord);
               }
               
               Entity entity = null;
               final List<Entity> targets = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
               
               double var7 = 0.0D;
               final Iterator<Entity> iter = targets.iterator();
               float range;
               
               while (iter.hasNext())
               {
                    final Entity target = iter.next();
                    
                    if (target.canBeCollidedWith() && ticksInAir >= 3)
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
                         var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                         
                         DamageSource source = null;
                         
                         if (owner == null)
                         {
                              source = new DamageSourceWeapon("tuxweapons:throwable", pos.entityHit, null, stack);
                         }
                         
                         else
                         {
                              source = new DamageSourceWeapon("tuxweapons:throwable", pos.entityHit, owner, stack);
                         }
                         
                         if (pos.entityHit instanceof EntityLivingBase && !hitEntity)
                         {
                              if (ticksInAir >= 3 && owner != null && pos.entityHit != owner)
                              {
                                   if (isBurning())
                                   {
                                        pos.entityHit.setFire(5);
                                   }
                                   
                                   worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F - 0.0F));
                                   
                                   motionX *= -0.10000000149011612D;
                                   motionY *= -0.10000000149011612D;
                                   motionZ *= -0.10000000149011612D;
                                   rotationYaw += 180.0F;
                                   prevRotationYaw += 180.0F;
                                   
                                   float damageDealt = 0;
                                   
                                   if (stack != null && stack.getItem() instanceof ItemWeapon)
                                   {
                                        damageDealt = ((ItemWeapon) stack.getItem()).itemMaterial.getDamageVsEntity() + 5;
                                   }
                                   
                                   else if (stack != null && stack.getItem() instanceof ItemKnife)
                                   {
                                        damageDealt = ((ItemKnife) stack.getItem()).itemMaterial.getDamageVsEntity() + 2;
                                   }
                                   
                                   if (damageDealt != 0)
                                   {
                                        pos.entityHit.attackEntityFrom(source, damageDealt);
                                   }
                                   
                                   hitEntity = true;
                              }
                         }
                    }
                    else
                    {
                         xTile = pos.blockX;
                         yTile = pos.blockY;
                         zTile = pos.blockZ;
                         inTile = worldObj.getBlockId(xTile, yTile, zTile);
                         inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
                         motionX = (float) (pos.hitVec.xCoord - posX);
                         motionY = (float) (pos.hitVec.yCoord - posY);
                         motionZ = (float) (pos.hitVec.zCoord - posZ);
                         var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                         posX -= motionX / var20 * 0.05000000074505806D;
                         posY -= motionY / var20 * 0.05000000074505806D;
                         posZ -= motionZ / var20 * 0.05000000074505806D;
                         worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F - 0.0F));
                         inGround = true;
                         arrowShake = 7;
                         setIsCritical(false);
                    }
               }
               
               if (getIsCritical())
               {
                    for (int var21 = 0; var21 < 4; ++var21)
                    {
                         worldObj.spawnParticle("crit", posX + motionX * var21 / 4.0D, posY + motionY * var21 / 4.0D, posZ + motionZ * var21 / 4.0D, -motionX, -motionY + 0.2D, -motionZ);
                    }
               }
               
               posX += motionX;
               posY += motionY;
               posZ += motionZ;
               var20 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
               final float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
               
               for (rotationPitch = (float) (Math.atan2(motionY, f1) * 180D / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F)
               {
               }
               
               for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F)
               {
               }
               
               for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F)
               {
               }
               
               for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F)
               {
               }
               
               rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
               rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
               float var23 = 0.99F;
               range = 0.05F;
               
               if (isInWater())
               {
                    for (int var26 = 0; var26 < 4; ++var26)
                    {
                         final float var27 = 0.25F;
                         worldObj.spawnParticle("bubble", posX - motionX * var27, posY - motionY * var27, posZ - motionZ * var27, motionX, motionY, motionZ);
                    }
                    
                    var23 = 0.8F;
               }
               
               motionX *= var23;
               motionY *= var23;
               motionZ *= var23;
               motionY -= range;
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
          nbtTag.setByte("shake", (byte) arrowShake);
          nbtTag.setByte("inGround", (byte) (inGround ? 1 : 0));
          nbtTag.setByte("pickup", (byte) canBePickedUp);
          nbtTag.setByte("hitEntity", (byte) (hitEntity ? 1 : 0));
          
          final NBTTagCompound tag = new NBTTagCompound();
          stack.writeToNBT(tag);
          nbtTag.setCompoundTag("stack", tag);
          
          if (owner != null)
          {
               nbtTag.setInteger("owner", owner.entityId);
          }
     }
     
     
     
     
     @Override
     public void readEntityFromNBT (final NBTTagCompound nbtTag)
     {
          xTile = nbtTag.getShort("xTile");
          yTile = nbtTag.getShort("yTile");
          zTile = nbtTag.getShort("zTile");
          inTile = nbtTag.getByte("inTile") & 255;
          inData = nbtTag.getByte("inData") & 255;
          arrowShake = nbtTag.getByte("shake") & 255;
          inGround = nbtTag.getByte("inGround") == 1;
          canBePickedUp = nbtTag.getByte("pickup");
          hitEntity = nbtTag.getByte("hitEntity") == 1;
          
          stack = new ItemStack(0, 0, 0);
          stack.readFromNBT(nbtTag.getCompoundTag("stack"));
          
          if (nbtTag.hasKey("owner"))
          {
               ownerID = nbtTag.getInteger("owner");
               owner = worldObj.getEntityByID(ownerID);
          }
     }
     
     
     
     
     @Override
     public void onCollideWithPlayer (final EntityPlayer player)
     {
          if (Assets.isClient())
          {
               if (inGround && arrowShake <= 0)
               {
                    doCollide(player);
                    
                    player.onItemPickup(this, 1);
                    setDead();
                    kill();
                    isDead = true;
                    
                    playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    
                    PacketDispatcher.sendPacketToServer(Assets.populatePacket(new PacketThrowablePickup(player.entityId, entityId)));
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
     
     
     
     
     @Override
     public void writeSpawnData (final ByteArrayDataOutput data)
     {
          data.writeShort(stack.itemID);
          data.writeInt(ownerID);
     }
     
     
     
     
     @Override
     public void readSpawnData (final ByteArrayDataInput data)
     {
          stack = new ItemStack(data.readShort(), 1, 0);
          ownerID = data.readInt();
          owner = worldObj.getEntityByID(ownerID);
     }
}
