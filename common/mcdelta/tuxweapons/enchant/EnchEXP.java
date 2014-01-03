package mcdelta.tuxweapons.enchant;

import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchEXP extends EnchantmentTW
{
     public EnchEXP (String name, int weight, EnumEnchantmentType type)
     {
          super(name, weight, type);
     }
     
     
     
     
     @Override
     public int getMinEnchantability (int enchLevel)
     {
          return 15 + (enchLevel - 1) * 9;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (int enchLevel)
     {
          return getMinEnchantability(enchLevel) + 50;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 2;
     }
}
