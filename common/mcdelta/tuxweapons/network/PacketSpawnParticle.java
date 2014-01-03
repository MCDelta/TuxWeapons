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
          this.particle = i;
          this.x = d1;
          this.y = d2;
          this.z = d3;
     }
     
     
     
     
     @Override
     public void writeData (final DataOutputStream data) throws IOException
     {
          data.writeInt(this.particle);
          data.writeDouble(this.x);
          data.writeDouble(this.y);
          data.writeDouble(this.z);
     }
     
     
     
     
     @Override
     public void readData (final DataInputStream data) throws IOException
     {
          this.particle = data.readInt();
          this.x = data.readDouble();
          this.y = data.readDouble();
          this.z = data.readDouble();
     }
     
     
     
     
     @Override
     public void execute (final INetworkManager manager, final Player playerParam)
     {
          final EntityPlayer player = (EntityPlayer) playerParam;
          
          EnumParticles.values()[this.particle].spawnParticleFromPacket(player, this.x, this.y, this.z);
     }
}
