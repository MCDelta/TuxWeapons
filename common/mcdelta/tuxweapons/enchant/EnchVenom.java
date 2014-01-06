package mcdelta.tuxweapons.enchant;

import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchVenom extends EnchantmentTW
{
     public EnchVenom (final String name, final int weight, final EnumEnchantmentType type)
     {
          super(name, weight, type);
     }
     
     
     
     
     @Override
     public int getMinEnchantability (final int enchLevel)
     {
          return 10 + 20 * (enchLevel - 1);
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (final int enchLevel)
     {
          return getMinEnchantability(enchLevel) + 50;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 2;
     }
}
