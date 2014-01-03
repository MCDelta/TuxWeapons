package mcdelta.tuxweapons.entity;

import mcdelta.tuxweapons.data.PlayerData;
import mcdelta.tuxweapons.data.TWNBTTags;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityGrappHook extends EntityTWThrowable
{
    public EntityGrappHook(World world)
    {
        super(world);
    }

    public EntityGrappHook(World world, EntityLivingBase living, float charge, ItemStack item)
    {
        super(world, living, charge, item);

        if (owner instanceof EntityPlayer)
        {
            PlayerData data = new PlayerData((EntityPlayer) owner);
            data.tag.setInteger(TWNBTTags.GRAPP, entityId);
            data.save();
        }
    }

    @Override
    public void doCollide(EntityPlayer player)
    {
        if (owner != null)
        {
            PlayerData data = new PlayerData((EntityPlayer) owner);
            data.tag.setInteger(TWNBTTags.GRAPP, -1);
            data.save();
        }
    }
}
