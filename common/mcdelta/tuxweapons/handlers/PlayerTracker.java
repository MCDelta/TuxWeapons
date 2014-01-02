package mcdelta.tuxweapons.handlers;

import mcdelta.core.data.NBTTags;
import mcdelta.core.data.PlayerData;
import mcdelta.tuxweapons.entity.EntityGrappHook;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker
{
     
     @Override
     public void onPlayerLogin (EntityPlayer player)
     {
          PlayerData data = new PlayerData(player);
          if (player.worldObj.getEntityByID(data.tag.getInteger(NBTTags.GRAPP)) instanceof EntityGrappHook)
          {
               EntityGrappHook grappHook = (EntityGrappHook) player.worldObj.getEntityByID(data.tag.getInteger(NBTTags.GRAPP));
               if (grappHook != null)
               {
                    grappHook.setDead();
               }
               data.tag.setInteger(NBTTags.GRAPP, -1);
               data.save();
          }
     }
     
     
     
     
     @Override
     public void onPlayerLogout (EntityPlayer player)
     {
          PlayerData data = new PlayerData(player);
          if (player.worldObj.getEntityByID(data.tag.getInteger(NBTTags.GRAPP)) instanceof EntityGrappHook)
          {
               EntityGrappHook grappHook = (EntityGrappHook) player.worldObj.getEntityByID(data.tag.getInteger(NBTTags.GRAPP));
               if (grappHook != null)
               {
                    grappHook.setDead();
               }
               data.tag.setInteger(NBTTags.GRAPP, -1);
               data.save();
          }
     }
     
     
     
     
     @Override
     public void onPlayerChangedDimension (EntityPlayer player)
     {
          PlayerData data = new PlayerData(player);
          if (player.worldObj.getEntityByID(data.tag.getInteger(NBTTags.GRAPP)) instanceof EntityGrappHook)
          {
               EntityGrappHook grappHook = (EntityGrappHook) player.worldObj.getEntityByID(data.tag.getInteger(NBTTags.GRAPP));
               if (grappHook != null)
               {
                    grappHook.setDead();
               }
               data.tag.setInteger(NBTTags.GRAPP, -1);
               data.save();
          }
     }
     
     
     
     
     @Override
     public void onPlayerRespawn (EntityPlayer player)
     {
          PlayerData data = new PlayerData(player);
          if (player.worldObj.getEntityByID(data.tag.getInteger(NBTTags.GRAPP)) instanceof EntityGrappHook)
          {
               EntityGrappHook grappHook = (EntityGrappHook) player.worldObj.getEntityByID(data.tag.getInteger(NBTTags.GRAPP));
               if (grappHook != null)
               {
                    grappHook.setDead();
               }
               data.tag.setInteger(NBTTags.GRAPP, -1);
               data.save();
          }
     }
}
