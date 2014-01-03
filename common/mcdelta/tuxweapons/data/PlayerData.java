package mcdelta.tuxweapons.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerData
{
     public NBTTagCompound tag;
     private EntityPlayer  player;
     
     
     
     
     public PlayerData (EntityPlayer player)
     {
          this.player = player;
          this.tag = player.getEntityData().getCompoundTag("MCDeltaInfo");
          
          if (this.tag == null)
          {
               this.tag = new NBTTagCompound();
               save();
          }
     }
     
     
     
     
     public void save ()
     {
          this.player.getEntityData().setCompoundTag("MCDeltaInfo", this.tag);
     }
}
