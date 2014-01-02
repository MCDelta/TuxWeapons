package mcdelta.tuxweapons.block;

import java.util.Random;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.Position;
import mcdelta.tuxweapons.block.tileentity.TileBrewStand;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.item.ItemPotion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBrewStand extends BlockBrewingStand
{
     public BlockBrewStand (int id)
     {
          super(id);
     }
     
     
     
     
     public TileEntity createNewTileEntity (World world)
     {
          return new TileBrewStand();
     }
}
