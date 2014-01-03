package mcdelta.tuxweapons.enchant;

import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchEXP extends EnchantmentTW
{
     public EnchEXP (final String name, final int weight, final EnumEnchantmentType type)
     {
          super(name, weight, type);
     }
     
     
     
     
     @Override
     public int getMinEnchantability (final int enchLevel)
     {
          return 15 + (enchLevel - 1) * 9;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (final int enchLevel)
     {
          return this.getMinEnchantability(enchLevel) + 50;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 2;
     }
}
