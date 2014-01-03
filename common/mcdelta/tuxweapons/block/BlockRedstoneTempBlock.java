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
     public int isProvidingWeakPower (IBlockAccess world, int x, int y, int z, int par5)
     {
          return 15;
     }
     
     
     
     
     @Override
     public int isProvidingStrongPower (IBlockAccess world, int x, int y, int z, int side)
     {
          return this.getPowerSupply(world.getBlockMetadata(x, y, z));
     }
     
     
     
     
     @Override
     public int tickRate (World idWorld)
     {
          return 1;
     }
     
     
     
     
     @Override
     public void updateTick (World idWorld, int x, int y, int z, Random par5Random)
     {
          idWorld.setBlockToAir(x, y, z);
     }
     
     
     
     
     @Override
     public void randomDisplayTick (World idWorld, int x, int y, int z, Random par5Random)
     {
          for (int i = 0; i <= 5; i++)
          {
               double ii = 1;
               
               double parX = ((Math.random() * ii));
               double parY = ((Math.random() * ii));
               double parZ = ((Math.random() * ii));
               
               idWorld.spawnParticle("reddust", x + parX, y + parY, z + parZ, 0.0D, 0.0D, 0.0D);
          }
          
     }
     
     
     
     
     public AxisAlignedBB getCollisionBoundingBoxFromPool (World idWorld, int x, int y, int z)
     {
          return null;
     }
     
     
     
     
     public MovingObjectPosition collisionRayTrace (World idWorld, int x, int y, int z, Vec3 par5Vec3, Vec3 par6Vec3)
     {
          float f = 0.15F;
          
          this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
          
          return super.collisionRayTrace(idWorld, x, y, z, par5Vec3, par6Vec3);
     }
     
     
     
     
     public boolean isBlockReplaceable (World world, int x, int y, int z)
     {
          return true;
     }
     
     
     
     
     public int quantityDropped (Random idRandom)
     {
          return 0;
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public int getRenderBlockPass ()
     {
          return 0;
     }
     
     
     
     
     public boolean isOpaqueCube ()
     {
          return false;
     }
     
     
     
     
     public boolean renderAsNormalBlock ()
     {
          return false;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void registerIcons (IconRegister iconRegister)
     {
          this.blockIcon = iconRegister.registerIcon(TuxWeapons.MOD_ID + ":" + "air");
     }
     
     
     
     
     protected int getPowerSupply (int i)
     {
          return 15;
     }
}
