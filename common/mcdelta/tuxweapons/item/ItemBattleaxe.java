package mcdelta.tuxweapons.item;

import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ItemMaterial;
import mcdelta.tuxweapons.TuxWeapons;

public class ItemBattleaxe extends ItemWeapon
{

    public ItemBattleaxe(ItemMaterial mat)
    {
        super("battleaxe", TuxWeapons.instance, mat, 6.0F);
        setMaxDamage((int) (mat.getMaxUses() * 0.8F));
    }
}
