package mcdelta.tuxweapons.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntitySpear extends EntityTWThrowable
{
     public EntitySpear (final World world)
     {
          super(world);
     }
     
     
     
     
     public EntitySpear (final World world, final EntityLivingBase living, final float charge, final ItemStack item)
     {
          super(world, living, charge, item);
     }
}
