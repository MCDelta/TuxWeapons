package mcdelta.tuxweapons.enchant;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

public class EnchDrawback extends EnchantmentTW
{
    public EnchDrawback(String name, int weight, EnumEnchantmentType type)
    {
        super(name, weight, type);
    }

    @Override
    public int getMinEnchantability(int enchLevel)
    {
        return (enchLevel) * 10;
    }

    @Override
    public int getMaxEnchantability(int enchLevel)
    {
        return getMinEnchantability(enchLevel) + 20;
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() instanceof ItemBow;
    }
}
