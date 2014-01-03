package mcdelta.tuxweapons.entity;

import mcdelta.tuxweapons.config.TWSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDynamite extends EntityThrowable
{

    protected boolean inGround = false;
    public int throwableShake = 0;

    private int ticksInAir = 0;
    private int fuse = 30;

    double bounceFactor;

    public EntityDynamite(World world)
    {
        super(world);
        bounceFactor = 20;
    }

    public EntityDynamite(World world, EntityPlayer living)
    {
        super(world, living);
    }

    public EntityDynamite(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition pos)
    {
        if (pos.entityHit != null)
        {
            byte b0 = 0;

            pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), b0);
        }

        fuse = fuse - 10;

    }

    @Override
    public void onUpdate()
    {
        double prevVelX = motionX;
        double prevVelY = motionY;
        double prevVelZ = motionZ;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        moveEntity(motionX, motionY, motionZ);
        ++ticksInAir;

        if (motionX != prevVelX)
        {
            motionX = -bounceFactor * prevVelX;
        }

        if (motionY != prevVelY)
        {
            motionY = -bounceFactor * prevVelY;
        } else
        {
            motionY -= 0.04;
        }

        if (motionZ != prevVelZ)
        {
            motionZ = -bounceFactor * prevVelZ;
        }

        motionX *= 0.9;
        motionY *= 0.9;
        motionZ *= 0.9;

        if (ticksInAir >= fuse)
        {
            if (!worldObj.isRemote)
            {
                worldObj.createExplosion(this, posX, posY, posZ, 3, TWSettings.GREIFING);
            }

            setDead();
            kill();
        }

        if (worldObj.rand.nextInt(3) == 0)
        {
            worldObj.spawnParticle("cloud", posX, posY, posZ, 0, 0, 0);
        }
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0.03F;
    }
}
