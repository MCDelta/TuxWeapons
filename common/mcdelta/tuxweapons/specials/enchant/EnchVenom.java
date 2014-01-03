package mcdelta.tuxweapons.specials.enchant;

import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchVenom extends EnchantmentTW
{
    public EnchVenom(String name, int weight, EnumEnchantmentType type)
    {
        super(name, weight, type);
    }

    @Override
    public int getMinEnchantability(int enchLevel)
    {
        return 10 + (20 * (enchLevel - 1));
    }

    @Override
    public int getMaxEnchantability(int enchLevel)
    {
        return getMinEnchantability(enchLevel) + 50;
    }

    @Override
    public int getMaxLevel()
    {
        return 2;
    }
}
