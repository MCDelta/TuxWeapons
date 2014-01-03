package mcdelta.tuxweapons.block;

import mcdelta.core.block.BlockDelta;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockTW extends BlockDelta
{
     public BlockTW (final String s, final Material mat)
     {
          super(TuxWeapons.instance, s, mat);
          
          this.setCreativeTab(CreativeTabs.tabBlock);
     }
}
