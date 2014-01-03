package mcdelta.tuxweapons.enchant;

import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchStrike extends EnchantmentTW
{
     public EnchStrike (final String name, final int weight, final EnumEnchantmentType type)
     {
          super(name, weight, type);
     }
     
     
     
     
     @Override
     public int getMinEnchantability (final int enchLevel)
     {
          return enchLevel * 10;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (final int enchLevel)
     {
          return this.getMinEnchantability(enchLevel) + 20;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 3;
     }
}
