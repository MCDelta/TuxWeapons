package mcdelta.tuxweapons.block;

import mcdelta.core.EnumMCDMods;
import mcdelta.core.block.BlockDelta;
import mcdelta.tuxweapons.block.tileentity.TileBrewStand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockTW extends BlockDelta
{
     public static BlockRedstoneTempBlock redstoneTmpBlock = new BlockRedstoneTempBlock();
     public static BlockBrewStand         brewStandMCD;
     
     static
     {
          Block.blocksList[117] = null;
          brewStandMCD = (BlockBrewStand) new BlockBrewStand(117).setHardness(0.5F).setLightValue(0.125F).setUnlocalizedName("brewingStand").setTextureName("brewing_stand");
          GameRegistry.registerBlock(brewStandMCD, "brewStandMCD");
          GameRegistry.registerTileEntity(TileBrewStand.class, "tileBrewStandMCD");
     }
     
     
     
     
     public BlockTW (String s, Material mat)
     {
          super(EnumMCDMods.TUXWEAPONS, s, mat);
          
          this.setCreativeTab(CreativeTabs.tabBlock);
     }
}
