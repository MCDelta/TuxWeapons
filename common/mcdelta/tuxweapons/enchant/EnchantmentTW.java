package mcdelta.tuxweapons.enchant;

import mcdelta.core.enchant.EnchantmentDelta;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentTW extends EnchantmentDelta
{
     protected int enchantLvlMin = 0;
     protected int enchantLvlMax = 5;
     
     
     
     
     public EnchantmentTW (final String s, final int rarity, final EnumEnchantmentType enchType)
     {
          super(TuxWeapons.instance, s, rarity, enchType);
     }
     
     
     
     
     public EnchantmentTW setMinMax (final int i, final int i2)
     {
          enchantLvlMin = i;
          enchantLvlMax = i2;
          return this;
     }
     
     
     
     
     @Override
     public int getMinEnchantability (final int enchLevel)
     {
          return enchantLvlMin;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (final int enchLevel)
     {
          return enchantLvlMax;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 1;
     }
     
}
