package mcdelta.tuxweapons.item;

import mcdelta.core.item.ItemDelta;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.creativetab.CreativeTabs;

public class ItemTW extends ItemDelta
{
     public ItemTW (String s, boolean b)
     {
          super(TuxWeapons.instance, s, b);
          this.setCreativeTab(CreativeTabs.tabCombat);
     }
     
     
     
     
     public ItemTW (String s)
     {
          super(TuxWeapons.instance, s);
          this.setCreativeTab(CreativeTabs.tabCombat);
     }    
}
