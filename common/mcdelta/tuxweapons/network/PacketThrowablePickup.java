package mcdelta.tuxweapons.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mcdelta.core.network.PacketDelta;
import mcdelta.tuxweapons.entity.EntityTWThrowable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.Player;

public class PacketThrowablePickup extends PacketDelta
{
     
     private int playerId;
     private int throwableId;
     
     
     
     
     public PacketThrowablePickup ()
     {
          super(3);
     }
     
     
     
     
     public PacketThrowablePickup (int i, int i2)
     {
          super(3);
          playerId = i;
          throwableId = i2;
     }
     
     
     
     
     @Override
     public void writeData (DataOutputStream data) throws IOException
     {
          data.writeInt(playerId);
          data.writeInt(throwableId);
     }
     
     
     
     
     @Override
     public void readData (DataInputStream data) throws IOException
     {
          playerId = data.readInt();
          throwableId = data.readInt();
     }
     
     
     
     
     @Override
     public void execute (INetworkManager manager, Player playerParam)
     {
          EntityPlayer entity = (EntityPlayer) playerParam;
          World world = entity.worldObj;
          
          EntityPlayer player = (EntityPlayer) world.getEntityByID(playerId);
          EntityTWThrowable throwable = (EntityTWThrowable) world.getEntityByID(throwableId);
          
          throwable.doCollide(player);
          
          if (throwable != null && player.capabilities.isCreativeMode != true && throwable.canBePickedUp == 1)
          {
               if (!player.inventory.addItemStackToInventory(throwable.stack))
               {
                    world.spawnEntityInWorld(new EntityItem(world, throwable.posX, throwable.posY, throwable.posZ, throwable.stack));
               }
          }
          
          if (throwable != null)
          {
               player.onItemPickup(throwable, 1);
               throwable.setDead();
               throwable.isDead = true;
          }
     }
}
