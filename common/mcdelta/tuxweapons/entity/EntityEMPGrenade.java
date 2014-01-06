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
               
               pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), b0);
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
               
               if (worldObj.isAirBlock(i, j, k))
               {
                    if (!worldObj.isRemote)
                    {
                         worldObj.setBlock(i, j, k, block);
                    }
                    if (!worldObj.isRemote)
                    {
                         worldObj.scheduleBlockUpdate(i, j, k, block, 20);
                    }
               }
          }
          
          for (int i = 0; i <= 75; i++)
          {
               final double ii = 4;
               
               final double parX = Math.random() * ii - ii / 2;
               final double parY = Math.random() * ii - ii / 2;
               final double parZ = Math.random() * ii - ii / 2;
               
               worldObj.spawnParticle("reddust", posX + parX, posY + parY, posZ + parZ, 0.0D, 0.0D, 0.0D);
          }
          
          setDead();
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
          ticksInAir++;
          
          final int block = TWContent.redstoneTmpBlock.blockID;
          
          if (worldObj.isAirBlock((int) posX, (int) posY, (int) posZ))
          {
               if (!worldObj.isRemote)
               {
                    worldObj.setBlock((int) posX, (int) posY, (int) posZ, block);
               }
               if (!worldObj.isRemote)
               {
                    worldObj.scheduleBlockUpdate((int) posX, (int) posY, (int) posZ, block, 10);
               }
          }
          
          if (ticksInAir >= 20)
          {
               final int x = (int) posX;
               final int j = (int) posY;
               final int k = (int) posZ;
               
               if (worldObj.isAirBlock(x, j, k))
               {
                    if (!worldObj.isRemote)
                    {
                         worldObj.setBlock(x, j, k, block);
                    }
                    if (!worldObj.isRemote)
                    {
                         worldObj.scheduleBlockUpdate(x, j, k, block, 20);
                    }
               }
               
               for (int i = 0; i <= 75; i++)
               {
                    final double ii = 4;
                    
                    final double parX = Math.random() * ii - ii / 2;
                    final double parY = Math.random() * ii - ii / 2;
                    final double parZ = Math.random() * ii - ii / 2;
                    
                    worldObj.spawnParticle("reddust", posX + parX, posY + parY, posZ + parZ, 0.0D, 0.0D, 0.0D);
               }
               
               setDead();
          }
     }
}
