package mcdelta.tuxweapons.item;

import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ItemMaterial;
import mcdelta.tuxweapons.TuxWeapons;

public class ItemMace extends ItemWeapon
{
     
     public ItemMace (final ItemMaterial mat)
     {
          super("mace", TuxWeapons.instance, mat, 1.0F);
          this.setMaxDamage((int) (mat.maxUses() * 0.9F));
     }
}
