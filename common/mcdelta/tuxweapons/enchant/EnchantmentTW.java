package mcdelta.tuxweapons.enchant;

import mcdelta.core.enchant.EnchantmentDelta;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentTW extends EnchantmentDelta
{
    protected int enchantLvlMin = 0;
    protected int enchantLvlMax = 5;

    public EnchantmentTW(String s, int rarity, EnumEnchantmentType enchType)
    {
        super(TuxWeapons.instance, s, rarity, enchType);
    }

    public EnchantmentTW setMinMax(int i, int i2)
    {
        enchantLvlMin = i;
        enchantLvlMax = i2;
        return this;
    }

    @Override
    public int getMinEnchantability(int enchLevel)
    {
        return enchantLvlMin;
    }

    @Override
    public int getMaxEnchantability(int enchLevel)
    {
        return enchantLvlMax;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

}
