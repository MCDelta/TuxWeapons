package mcdelta.tuxweapons.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntitySpear extends EntityTWThrowable
{
    public EntitySpear(World world)
    {
        super(world);
    }

    public EntitySpear(World world, EntityLivingBase living, float charge, ItemStack item)
    {
        super(world, living, charge, item);
    }
}
