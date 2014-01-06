package mcdelta.tuxweapons.item;

import mcdelta.core.item.ItemDelta;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.creativetab.CreativeTabs;

public class ItemTW extends ItemDelta
{
     public ItemTW (final String s, final boolean b)
     {
          super(TuxWeapons.instance, s, b);
          setCreativeTab(CreativeTabs.tabCombat);
     }
     
     
     
     
     public ItemTW (final String s)
     {
          super(TuxWeapons.instance, s);
          setCreativeTab(CreativeTabs.tabCombat);
     }
}
