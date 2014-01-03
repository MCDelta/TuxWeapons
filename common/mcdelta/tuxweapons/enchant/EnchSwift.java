package mcdelta.tuxweapons.enchant;

import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchSwift extends EnchantmentTW
{
     public EnchSwift (String name, int weight, EnumEnchantmentType type)
     {
          super(name, weight, type);
     }
     
     
     
     
     @Override
     public int getMinEnchantability (int enchLevel)
     {
          return (enchLevel + 1) * 10;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (int enchLevel)
     {
          return getMinEnchantability(enchLevel) + 20;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 3;
     }
}
