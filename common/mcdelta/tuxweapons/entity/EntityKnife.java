package mcdelta.tuxweapons.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityKnife extends EntityTWThrowable
{
     public int spin = 0;
     
     
     
     
     public EntityKnife (final World world)
     {
          super(world);
     }
     
     
     
     
     public EntityKnife (final World world, final EntityLivingBase living, final float charge, final ItemStack item)
     {
          super(world, living, charge, item);
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          super.onUpdate();
          this.spin += 10;
     }
}
