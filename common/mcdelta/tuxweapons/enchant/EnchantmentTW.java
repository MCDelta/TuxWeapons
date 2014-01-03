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
          this.enchantLvlMin = i;
          this.enchantLvlMax = i2;
          return this;
     }
     
     
     
     
     @Override
     public int getMinEnchantability (final int enchLevel)
     {
          return this.enchantLvlMin;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (final int enchLevel)
     {
          return this.enchantLvlMax;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 1;
     }
     
}
