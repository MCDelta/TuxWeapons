package mcdelta.tuxweapons.item;

import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ToolMaterial;
import mcdelta.tuxweapons.TuxWeapons;

public class ItemBattleaxe extends ItemWeapon
{
     
     public ItemBattleaxe (ToolMaterial mat)
     {
          super("battleaxe", TuxWeapons.instance, mat, 6.0F);
          this.setMaxDamage((int) ((float) mat.getMaxUses() * 0.8F));
     }
}
