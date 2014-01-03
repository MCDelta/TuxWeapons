package mcdelta.tuxweapons.item;

import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ToolMaterial;
import mcdelta.tuxweapons.TuxWeapons;

public class ItemMace extends ItemWeapon
{
     
     public ItemMace (ToolMaterial mat)
     {
          super("mace", TuxWeapons.instance, mat, 1.0F);
          this.setMaxDamage((int) ((float) mat.getMaxUses() * 0.9F));
     }
}
