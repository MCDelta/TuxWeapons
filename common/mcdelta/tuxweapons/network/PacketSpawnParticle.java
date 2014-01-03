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
    private int particle;
    private double x;
    private double y;
    private double z;

    public PacketSpawnParticle()
    {
        super(2);
    }

    public PacketSpawnParticle(int i, double d1, double d2, double d3)
    {
        super(2);
        particle = i;
        x = d1;
        y = d2;
        z = d3;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        data.writeInt(particle);
        data.writeDouble(x);
        data.writeDouble(y);
        data.writeDouble(z);
    }

    @Override
    public void readData(DataInputStream data) throws IOException
    {
        particle = data.readInt();
        x = data.readDouble();
        y = data.readDouble();
        z = data.readDouble();
    }

    @Override
    public void execute(INetworkManager manager, Player playerParam)
    {
        EntityPlayer player = (EntityPlayer) playerParam;

        EnumParticles.values()[particle].spawnParticleFromPacket(player, x, y, z);
    }
}
