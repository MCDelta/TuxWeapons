package mcdelta.tuxweapons.specials.enchant;

import mcdelta.core.EnumMCDMods;
import mcdelta.core.config.Settings;
import mcdelta.core.special.enchant.EnchantmentDelta;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentTW extends EnchantmentDelta
{
     public static EnchSwift     swift         = new EnchSwift("swift", 2, EnumEnchantmentType.weapon);
     public static EnchStrike    strike        = new EnchStrike("strike", 3, EnumEnchantmentType.weapon);
     public static EnchVenom     venom         = new EnchVenom("venom", 5, EnumEnchantmentType.weapon);
     public static EnchDrawback  drawback      = new EnchDrawback("drawback", 5, EnumEnchantmentType.bow);
     public static EnchEXP       expIncrease   = new EnchEXP("expIncrease", 4, EnumEnchantmentType.weapon);
     public static EnchantmentTW hardened      = new EnchantmentTW("hardened", 2, EnumEnchantmentType.armor_torso).setMinMax(15, 30);
     
     protected int               enchantLvlMin = 0;
     protected int               enchantLvlMax = 5;
     
     
     
     
     public EnchantmentTW (String s, int rarity, EnumEnchantmentType enchType)
     {
          super(EnumMCDMods.TUXWEAPONS, s, rarity, enchType);
     }
     
     
     
     
     public EnchantmentTW setMinMax (int i, int i2)
     {
          this.enchantLvlMin = i;
          this.enchantLvlMax = i2;
          return this;
     }
     
     
     
     
     @Override
     public int getMinEnchantability (int enchLevel)
     {
          return enchantLvlMin;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (int enchLevel)
     {
          return enchantLvlMax;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 1;
     }
     
}
