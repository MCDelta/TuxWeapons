package mcdelta.tuxweapons.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerData
{
    public NBTTagCompound tag;
    private final EntityPlayer player;

    public PlayerData(EntityPlayer player)
    {
        this.player = player;
        tag = player.getEntityData().getCompoundTag("MCDeltaInfo");

        if (tag == null)
        {
            tag = new NBTTagCompound();
            save();
        }
    }

    public void save()
    {
        player.getEntityData().setCompoundTag("MCDeltaInfo", tag);
    }
}
