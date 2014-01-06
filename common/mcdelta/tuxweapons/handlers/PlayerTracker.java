package mcdelta.tuxweapons.handlers;

import mcdelta.tuxweapons.data.PlayerData;
import mcdelta.tuxweapons.data.TWNBTTags;
import mcdelta.tuxweapons.entity.EntityGrappHook;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker
{
     
     private void removeGrappHook (final EntityPlayer player)
     {
          final PlayerData data = new PlayerData(player);
          if (player.worldObj.getEntityByID(data.tag.getInteger(TWNBTTags.GRAPP)) instanceof EntityGrappHook)
          {
               final EntityGrappHook grappHook = (EntityGrappHook) player.worldObj.getEntityByID(data.tag.getInteger(TWNBTTags.GRAPP));
               if (grappHook != null)
               {
                    grappHook.setDead();
               }
               data.tag.setInteger(TWNBTTags.GRAPP, -1);
               data.save();
          }
     }
     
     
     
     
     @Override
     public void onPlayerLogin (final EntityPlayer player)
     {
          removeGrappHook(player);
     }
     
     
     
     
     @Override
     public void onPlayerLogout (final EntityPlayer player)
     {
          removeGrappHook(player);
     }
     
     
     
     
     @Override
     public void onPlayerChangedDimension (final EntityPlayer player)
     {
          removeGrappHook(player);
     }
     
     
     
     
     @Override
     public void onPlayerRespawn (final EntityPlayer player)
     {
          removeGrappHook(player);
     }
}
