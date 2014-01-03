package mcdelta.tuxweapons.handlers;

import mcdelta.tuxweapons.PlayerData;
import mcdelta.tuxweapons.TWNBTTags;
import mcdelta.tuxweapons.entity.EntityGrappHook;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker
{
     
     private void removeGrappHook (EntityPlayer player)
     {
          PlayerData data = new PlayerData(player);
          if (player.worldObj.getEntityByID(data.tag.getInteger(TWNBTTags.GRAPP)) instanceof EntityGrappHook)
          {
               EntityGrappHook grappHook = (EntityGrappHook) player.worldObj.getEntityByID(data.tag.getInteger(TWNBTTags.GRAPP));
               if (grappHook != null)
               {
                    grappHook.setDead();
               }
               data.tag.setInteger(TWNBTTags.GRAPP, -1);
               data.save();
          }
     }
     
     
     
     
     @Override
     public void onPlayerLogin (EntityPlayer player)
     {
          removeGrappHook(player);
     }
     
     
     
     
     @Override
     public void onPlayerLogout (EntityPlayer player)
     {
          removeGrappHook(player);
     }
     
     
     
     
     @Override
     public void onPlayerChangedDimension (EntityPlayer player)
     {
          removeGrappHook(player);
     }
     
     
     
     
     @Override
     public void onPlayerRespawn (EntityPlayer player)
     {
          removeGrappHook(player);
     }
}
