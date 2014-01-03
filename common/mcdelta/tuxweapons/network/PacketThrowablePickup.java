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
     
     
     
     
     public PacketThrowablePickup (final int i, final int i2)
     {
          super(3);
          this.playerId = i;
          this.throwableId = i2;
     }
     
     
     
     
     @Override
     public void writeData (final DataOutputStream data) throws IOException
     {
          data.writeInt(this.playerId);
          data.writeInt(this.throwableId);
     }
     
     
     
     
     @Override
     public void readData (final DataInputStream data) throws IOException
     {
          this.playerId = data.readInt();
          this.throwableId = data.readInt();
     }
     
     
     
     
     @Override
     public void execute (final INetworkManager manager, final Player playerParam)
     {
          final EntityPlayer entity = (EntityPlayer) playerParam;
          final World world = entity.worldObj;
          
          final EntityPlayer player = (EntityPlayer) world.getEntityByID(this.playerId);
          final EntityTWThrowable throwable = (EntityTWThrowable) world.getEntityByID(this.throwableId);
          
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
