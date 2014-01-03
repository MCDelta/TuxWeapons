package mcdelta.tuxweapons.block;

import mcdelta.tuxweapons.block.tileentity.TileBrewStand;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBrewStand extends BlockBrewingStand
{
     public BlockBrewStand (final int id)
     {
          super(id);
     }
     
     
     
     
     @Override
     public TileEntity createNewTileEntity (final World world)
     {
          return new TileBrewStand();
     }
}
