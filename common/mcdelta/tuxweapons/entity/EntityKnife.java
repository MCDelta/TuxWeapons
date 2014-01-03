package mcdelta.tuxweapons.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityKnife extends EntityTWThrowable
{
    public int spin = 0;

    public EntityKnife(World world)
    {
        super(world);
    }

    public EntityKnife(World world, EntityLivingBase living, float charge, ItemStack item)
    {
        super(world, living, charge, item);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        spin += 10;
    }
}
