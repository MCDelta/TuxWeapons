package mcdelta.tuxweapons.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mcdelta.core.client.particle.EnumParticles;
import mcdelta.core.network.PacketDelta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

public class PacketSpawnParticle extends PacketDelta
{
     private int    particle;
     private double x;
     private double y;
     private double z;
     
     
     
     
     public PacketSpawnParticle ()
     {
          super(2);
     }
     
     
     
     
     public PacketSpawnParticle (final int i, final double d1, final double d2, final double d3)
     {
          super(2);
          particle = i;
          x = d1;
          y = d2;
          z = d3;
     }
     
     
     
     
     @Override
     public void writeData (final DataOutputStream data) throws IOException
     {
          data.writeInt(particle);
          data.writeDouble(x);
          data.writeDouble(y);
          data.writeDouble(z);
     }
     
     
     
     
     @Override
     public void readData (final DataInputStream data) throws IOException
     {
          particle = data.readInt();
          x = data.readDouble();
          y = data.readDouble();
          z = data.readDouble();
     }
     
     
     
     
     @Override
     public void execute (final INetworkManager manager, final Player playerParam)
     {
          final EntityPlayer player = (EntityPlayer) playerParam;
          
          EnumParticles.values()[particle].spawnParticleFromPacket(player, x, y, z);
     }
}
