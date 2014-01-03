package mcdelta.tuxweapons.entity;

import mcdelta.tuxweapons.TWContent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEMPGrenade extends EntityThrowable
{
     
     int ticksInAir;
     
     
     
     
     public EntityEMPGrenade (final World world)
     {
          super(world);
     }
     
     
     
     
     public EntityEMPGrenade (final World world, final EntityPlayer player)
     {
          super(world, player);
     }
     
     
     
     
     @Override
     protected void onImpact (final MovingObjectPosition pos)
     {
          if (pos.entityHit != null)
          {
               final byte b0 = 0;
               
               pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
          }
          else
          {
               int i = pos.blockX;
               int j = pos.blockY;
               int k = pos.blockZ;
               
               switch (pos.sideHit)
               {
                    case 0:
                         --j;
                    case 1:
                         ++j;
                    case 2:
                         --k;
                    case 3:
                         ++k;
                    case 4:
                         --i;
                    case 5:
                         ++i;
               }
               
               final int block = TWContent.redstoneTmpBlock.blockID;
               
               if (this.worldObj.isAirBlock(i, j, k))
               {
                    if (!this.worldObj.isRemote)
                    {
                         this.worldObj.setBlock(i, j, k, block);
                    }
                    if (!this.worldObj.isRemote)
                    {
                         this.worldObj.scheduleBlockUpdate(i, j, k, block, 20);
                    }
               }
          }
          
          for (int i = 0; i <= 75; i++)
          {
               final double ii = 4;
               
               final double parX = Math.random() * ii - ii / 2;
               final double parY = Math.random() * ii - ii / 2;
               final double parZ = Math.random() * ii - ii / 2;
               
               this.worldObj.spawnParticle("reddust", this.posX + parX, this.posY + parY, this.posZ + parZ, 0.0D, 0.0D, 0.0D);
          }
          
          this.setDead();
     }
     
     
     
     
     @Override
     protected float getGravityVelocity ()
     {
          return 0.05F;
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          super.onUpdate();
          this.ticksInAir++;
          
          final int block = TWContent.redstoneTmpBlock.blockID;
          
          if (this.worldObj.isAirBlock((int) this.posX, (int) this.posY, (int) this.posZ))
          {
               if (!this.worldObj.isRemote)
               {
                    this.worldObj.setBlock((int) this.posX, (int) this.posY, (int) this.posZ, block);
               }
               if (!this.worldObj.isRemote)
               {
                    this.worldObj.scheduleBlockUpdate((int) this.posX, (int) this.posY, (int) this.posZ, block, 10);
               }
          }
          
          if (this.ticksInAir >= 20)
          {
               final int x = (int) this.posX;
               final int j = (int) this.posY;
               final int k = (int) this.posZ;
               
               if (this.worldObj.isAirBlock(x, j, k))
               {
                    if (!this.worldObj.isRemote)
                    {
                         this.worldObj.setBlock(x, j, k, block);
                    }
                    if (!this.worldObj.isRemote)
                    {
                         this.worldObj.scheduleBlockUpdate(x, j, k, block, 20);
                    }
               }
               
               for (int i = 0; i <= 75; i++)
               {
                    final double ii = 4;
                    
                    final double parX = Math.random() * ii - ii / 2;
                    final double parY = Math.random() * ii - ii / 2;
                    final double parZ = Math.random() * ii - ii / 2;
                    
                    this.worldObj.spawnParticle("reddust", this.posX + parX, this.posY + parY, this.posZ + parZ, 0.0D, 0.0D, 0.0D);
               }
               
               this.setDead();
          }
     }
}
