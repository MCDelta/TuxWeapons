package mcdelta.tuxweapons.block;

import java.util.Random;

import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRedstoneTempBlock extends BlockTW
{
     
     public BlockRedstoneTempBlock ()
     {
          super("redstoneTmpBlock", Material.air);
          this.setCreativeTab(null);
          this.setTickRandomly(true);
     }
     
     
     
     
     @Override
     public boolean canProvidePower ()
     {
          return true;
     }
     
     
     
     
     @Override
     public int isProvidingWeakPower (final IBlockAccess world, final int x, final int y, final int z, final int par5)
     {
          return 15;
     }
     
     
     
     
     @Override
     public int isProvidingStrongPower (final IBlockAccess world, final int x, final int y, final int z, final int side)
     {
          return this.getPowerSupply(world.getBlockMetadata(x, y, z));
     }
     
     
     
     
     @Override
     public int tickRate (final World idWorld)
     {
          return 1;
     }
     
     
     
     
     @Override
     public void updateTick (final World idWorld, final int x, final int y, final int z, final Random par5Random)
     {
          idWorld.setBlockToAir(x, y, z);
     }
     
     
     
     
     @Override
     public void randomDisplayTick (final World idWorld, final int x, final int y, final int z, final Random par5Random)
     {
          for (int i = 0; i <= 5; i++)
          {
               final double ii = 1;
               
               final double parX = Math.random() * ii;
               final double parY = Math.random() * ii;
               final double parZ = Math.random() * ii;
               
               idWorld.spawnParticle("reddust", x + parX, y + parY, z + parZ, 0.0D, 0.0D, 0.0D);
          }
          
     }
     
     
     
     
     @Override
     public AxisAlignedBB getCollisionBoundingBoxFromPool (final World idWorld, final int x, final int y, final int z)
     {
          return null;
     }
     
     
     
     
     @Override
     public MovingObjectPosition collisionRayTrace (final World idWorld, final int x, final int y, final int z, final Vec3 par5Vec3, final Vec3 par6Vec3)
     {
          this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
          
          return super.collisionRayTrace(idWorld, x, y, z, par5Vec3, par6Vec3);
     }
     
     
     
     
     @Override
     public boolean isBlockReplaceable (final World world, final int x, final int y, final int z)
     {
          return true;
     }
     
     
     
     
     @Override
     public int quantityDropped (final Random idRandom)
     {
          return 0;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public int getRenderBlockPass ()
     {
          return 0;
     }
     
     
     
     
     @Override
     public boolean isOpaqueCube ()
     {
          return false;
     }
     
     
     
     
     @Override
     public boolean renderAsNormalBlock ()
     {
          return false;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void registerIcons (final IconRegister iconRegister)
     {
          this.blockIcon = iconRegister.registerIcon(TuxWeapons.MOD_ID + ":" + "air");
     }
     
     
     
     
     protected int getPowerSupply (final int i)
     {
          return 15;
     }
}
