package mcdelta.tuxweapons.item;

import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ItemMaterial;
import mcdelta.tuxweapons.TuxWeapons;

public class ItemMace extends ItemWeapon
{

    public ItemMace(ItemMaterial mat)
    {
        super("mace", TuxWeapons.instance, mat, 1.0F);
        setMaxDamage((int) (mat.getMaxUses() * 0.9F));
    }
}
