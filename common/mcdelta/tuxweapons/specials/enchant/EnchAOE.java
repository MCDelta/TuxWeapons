package mcdelta.tuxweapons.specials.enchant;

import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchAOE extends EnchantmentTW
{
     public EnchAOE (String name, int weight, EnumEnchantmentType type)
     {
          super(name, weight, type);
     }
     
     
     
     
     @Override
     public int getMinEnchantability (int enchLevel)
     {
          return (enchLevel + 2) * 10;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (int enchLevel)
     {
          return getMinEnchantability(enchLevel) + 50;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 3;
     }
}
