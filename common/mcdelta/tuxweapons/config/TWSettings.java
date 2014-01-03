package mcdelta.tuxweapons.config;


public class TWSettings
{
     public static String  DESCRIPTION_TUXWEAPONS     = "All extra configuration options concerning the mod TuxWeapons will be found here. Configure the mod to your wishes!";
     
     public static String  CATEGORY_DAMAGE_MODIFIER   = "DamageModifier";
     public static String  COMMENT_DAMAGE_MODIFIER    = "Mess around with the damage modifier settings (weaknesses/resistances)";
     
     public static String  CATEGORY_GENERAL           = "General";
     public static String  COMMENT_GENERAL            = "Various settings to help configure TuxWeapons";
     
     public static String  DAMAGE_MODIFIER_ENABLE_KEY = "enable weaknesses and resistances";
     public static boolean DAMAGE_MODIFIER_ENABLE     = true;
     
     public static String  DAMAGE_MODIFIER_WEAK_KEY   = "amount of damage to add vs weaknessed entities";
     public static int     DAMAGE_MODIFIER_WEAK       = 4;
     
     public static String  DAMAGE_MODIFIER_RESIST_KEY = "amount of damage to subtract vs resistant entities";
     public static int     DAMAGE_MODIFIER_RESIST     = 4;
     
     public static String  DAMAGE_MODIFIER_GOLD_KEY   = "amount of damage to add vs entities weaknessed to gold";
     public static int     DAMAGE_MODIFIER_GOLD       = 8;
     
     public static String  DAMAGE_MODIFIER_MACE_KEY   = "maximum amount of extra damage the mace can do";
     public static int     DAMAGE_MODIFIER_MACE       = 5;
     
     public static String  DAMAGE_MODIFIER_STRIKE_KEY = "maximum amount of extra damage the strike enchant can do (adds one per level)";
     public static int     DAMAGE_MODIFIER_STRIKE     = 2;
     
     public static String  GREIFING_KEY               = "enable griefing";
     public static boolean GREIFING                   = true;
}
