package mcdelta.tuxweapons.entity;

import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.block.BlockTW;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEMPGrenade extends EntityThrowable
{
     
     int ticksInAir;
     
     
     
     
     public EntityEMPGrenade (World world)
     {
          super(world);
     }
     
     
     
     
     public EntityEMPGrenade (World world, EntityPlayer player)
     {
          super(world, player);
     }
     
     
     
     
     @Override
     protected void onImpact (MovingObjectPosition pos)
     {
          if (pos.entityHit != null)
          {
               byte b0 = 0;
               
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
               
               int block = BlockTW.redstoneTmpBlock.blockID;
               
               if (this.worldObj.isAirBlock(i, j, k))
               {
                    if (!this.worldObj.isRemote)
                         this.worldObj.setBlock(i, j, k, block);
                    if (!this.worldObj.isRemote)
                         this.worldObj.scheduleBlockUpdate(i, j, k, block, 20);
               }
          }
          
          for (int i = 0; i <= 75; i++)
          {
               double ii = 4;
               
               double parX = ((Math.random() * ii) - (ii / 2));
               double parY = ((Math.random() * ii) - (ii / 2));
               double parZ = ((Math.random() * ii) - (ii / 2));
               
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
          ticksInAir++;
          
          int block = BlockTW.redstoneTmpBlock.blockID;
          
          if (this.worldObj.isAirBlock((int) this.posX, (int) this.posY, (int) this.posZ))
          {
               if (!this.worldObj.isRemote)
                    this.worldObj.setBlock((int) this.posX, (int) this.posY, (int) this.posZ, block);
               if (!this.worldObj.isRemote)
                    this.worldObj.scheduleBlockUpdate((int) this.posX, (int) this.posY, (int) this.posZ, block, 10);
          }
          
          if (ticksInAir >= 20)
          {
               int x = (int) this.posX;
               int j = (int) this.posY;
               int k = (int) this.posZ;
               
               if (this.worldObj.isAirBlock(x, j, k))
               {
                    if (!this.worldObj.isRemote)
                         this.worldObj.setBlock(x, j, k, block);
                    if (!this.worldObj.isRemote)
                         this.worldObj.scheduleBlockUpdate(x, j, k, block, 20);
               }
               
               for (int i = 0; i <= 75; i++)
               {
                    double ii = 4;
                    
                    double parX = ((Math.random() * ii) - (ii / 2));
                    double parY = ((Math.random() * ii) - (ii / 2));
                    double parZ = ((Math.random() * ii) - (ii / 2));
                    
                    this.worldObj.spawnParticle("reddust", this.posX + parX, this.posY + parY, this.posZ + parZ, 0.0D, 0.0D, 0.0D);
               }
               
               this.setDead();
          }
     }
}
