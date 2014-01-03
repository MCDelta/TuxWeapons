package mcdelta.tuxweapons.entity;

import mcdelta.tuxweapons.config.TWSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTWFireball extends EntityThrowable
{
     int explosionForce;
     int ticksInAir;
     
     
     
     
     public EntityTWFireball (final World world)
     {
          super(world);
     }
     
     
     
     
     public EntityTWFireball (final World world, final EntityPlayer player, final int charge)
     {
          super(world, player);
          this.explosionForce = charge;
     }
     
     
     
     
     @Override
     protected void onImpact (final MovingObjectPosition pos)
     {
          if (pos.entityHit != null)
          {
               final byte b0 = 0;
               
               pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
          }
          
          this.worldObj.spawnParticle("hugeexplosion", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
          
          this.explode();
     }
     
     
     
     
     @Override
     protected float getGravityVelocity ()
     {
          return 0;
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          super.onUpdate();
          this.ticksInAir++;
          
          if (this.ticksInAir >= 20)
          {
               this.explode();
          }
          
          if (this.inWater)
          {
               this.explode();
          }
          
          for (int i = 0; i < 2; i++)
          {
               final double xOffset = this.rand.nextDouble() - 0.5;
               final double yOffset = this.rand.nextDouble() - 0.5;
               final double zOffset = this.rand.nextDouble() - 0.5;
               
               this.worldObj.spawnParticle("flame", this.posX + xOffset, this.posY + yOffset, this.posZ + zOffset, 0, 0, 0);
          }
     }
     
     
     
     
     private void explode ()
     {
          final float i = this.explosionForce / 12;
          float explosionRadius;
          boolean onFire = false;
          
          if (i >= 5)
          {
               explosionRadius = 5;
          }
          
          else
          {
               explosionRadius = this.explosionForce / 12;
          }
          
          if (explosionRadius >= 4)
          {
               onFire = true;
          }
          
          boolean grief = true;
          
          if (!TWSettings.GREIFING)
          {
               onFire = false;
               grief = false;
          }
          
          this.worldObj.newExplosion((Entity) null, this.posX, this.posY, this.posZ, explosionRadius, onFire, grief);
          
          this.setDead();
     }
}
