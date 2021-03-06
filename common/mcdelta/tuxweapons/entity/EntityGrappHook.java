package mcdelta.tuxweapons.entity;

import mcdelta.tuxweapons.data.PlayerData;
import mcdelta.tuxweapons.data.TWNBTTags;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityGrappHook extends EntityTWThrowable
{
     public EntityGrappHook (final World world)
     {
          super(world);
     }
     
     
     
     
     public EntityGrappHook (final World world, final EntityLivingBase living, final float charge, final ItemStack item)
     {
          super(world, living, charge, item);
          
          if (owner instanceof EntityPlayer)
          {
               final PlayerData data = new PlayerData((EntityPlayer) owner);
               data.tag.setInteger(TWNBTTags.GRAPP, entityId);
               data.save();
          }
     }
     
     
     
     
     @Override
     public void doCollide (final EntityPlayer player)
     {
          if (owner != null)
          {
               final PlayerData data = new PlayerData((EntityPlayer) owner);
               data.tag.setInteger(TWNBTTags.GRAPP, -1);
               data.save();
          }
     }
}
