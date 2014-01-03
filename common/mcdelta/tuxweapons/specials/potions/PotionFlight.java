package mcdelta.tuxweapons.specials.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionFlight extends PotionTW
{
    public PotionFlight(String s, int color, int x, int y)
    {
        super(s, color, x, y);
        setEffectiveness(0.25D);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int i)
    {
        if (entity.worldObj.isRemote)
        {
            if (entity instanceof EntityPlayer)
            {
                GameSettings settings = null;
                settings = Minecraft.getMinecraft().gameSettings;

                if (settings.keyBindJump.pressed)
                {
                    entity.addVelocity(0.0F, 0.1F, 0.0F);
                }
            }
        }

        if (!entity.worldObj.isRemote && !(entity instanceof EntityPlayer))
        {
            entity.addVelocity(0.0F, 0.09F, 0.0F);

            if ((entity.posY >= 256) && (entity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, entity.boundingBox.expand(10, 10, 10)) == null))
            {
                entity.setDead();
            }
        }

        if (entity.motionY >= -0.0F)
        {
            entity.fallDistance = 0;
        }
    }

    @Override
    public boolean isReady(int i, int i2)
    {
        return true;
    }
}
