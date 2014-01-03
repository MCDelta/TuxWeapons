package mcdelta.tuxweapons.enchant;

import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchAOE extends EnchantmentTW
{
     public EnchAOE (final String name, final int weight, final EnumEnchantmentType type)
     {
          super(name, weight, type);
     }
     
     
     
     
     @Override
     public int getMinEnchantability (final int enchLevel)
     {
          return (enchLevel + 2) * 10;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (final int enchLevel)
     {
          return this.getMinEnchantability(enchLevel) + 50;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 3;
     }
}
