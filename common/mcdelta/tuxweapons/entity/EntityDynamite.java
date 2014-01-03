package mcdelta.tuxweapons.entity;

import mcdelta.tuxweapons.config.TWSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDynamite extends EntityThrowable
{
     
     protected boolean inGround       = false;
     public int        throwableShake = 0;
     
     private int       ticksInAir     = 0;
     private int       fuse           = 30;
     
     double            bounceFactor;
     
     
     
     
     public EntityDynamite (final World world)
     {
          super(world);
          this.bounceFactor = 20;
     }
     
     
     
     
     public EntityDynamite (final World world, final EntityPlayer living)
     {
          super(world, living);
     }
     
     
     
     
     public EntityDynamite (final World world, final double x, final double y, final double z)
     {
          super(world, x, y, z);
     }
     
     
     
     
     @Override
     protected void onImpact (final MovingObjectPosition pos)
     {
          if (pos.entityHit != null)
          {
               final byte b0 = 0;
               
               pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
          }
          
          this.fuse = this.fuse - 10;
          
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          final double prevVelX = this.motionX;
          final double prevVelY = this.motionY;
          final double prevVelZ = this.motionZ;
          this.prevPosX = this.posX;
          this.prevPosY = this.posY;
          this.prevPosZ = this.posZ;
          this.moveEntity(this.motionX, this.motionY, this.motionZ);
          ++this.ticksInAir;
          
          if (this.motionX != prevVelX)
          {
               this.motionX = -this.bounceFactor * prevVelX;
          }
          
          if (this.motionY != prevVelY)
          {
               this.motionY = -this.bounceFactor * prevVelY;
          }
          else
          {
               this.motionY -= 0.04;
          }
          
          if (this.motionZ != prevVelZ)
          {
               this.motionZ = -this.bounceFactor * prevVelZ;
          }
          
          this.motionX *= 0.9;
          this.motionY *= 0.9;
          this.motionZ *= 0.9;
          
          if (this.ticksInAir >= this.fuse)
          {
               if (!this.worldObj.isRemote)
               {
                    this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3, TWSettings.GREIFING);
               }
               
               this.setDead();
               this.kill();
          }
          
          if (this.worldObj.rand.nextInt(3) == 0)
          {
               this.worldObj.spawnParticle("cloud", this.posX, this.posY, this.posZ, 0, 0, 0);
          }
     }
     
     
     
     
     @Override
     protected float getGravityVelocity ()
     {
          return 0.03F;
     }
}
