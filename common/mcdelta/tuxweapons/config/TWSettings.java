package mcdelta.tuxweapons.config;

import static mcdelta.core.assets.Assets.*;
import mcdelta.core.DeltaCore;
import mcdelta.core.assets.Assets;
import mcdelta.core.config.Settings;
import net.minecraftforge.common.Configuration;

public class TWSettings
{
     private static String DESCRIPTION_TUXWEAPONS     = "All extra configuration options concerning the mod TuxWeapons will be found here. Configure the mod to your wishes!";
     
     private static String CATEGORY_DAMAGE_MODIFIER   = "Damage Modifier";
     private static String COMMENT_DAMAGE_MODIFIER    = "Mess around with the damage modifier settings (weaknesses/resistances)";
     
     private static String CATEGORY_GENERAL           = "General";
     private static String COMMENT_GENERAL            = "Various settings to help configure TuxWeapons";
     
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
     
     
     
     
     public static void initTWConfig (Configuration cfg)
     {
          cfg.load();
          
          try
          {
               Class c = TWSettings.class;
               
               String category = CATEGORY_GENERAL;
               cfg.addCustomCategoryComment(category, DeltaCore.config.buildFancyComment(COMMENT_GENERAL, cfg.NEW_LINE, 20));
               DeltaCore.config.getBoolean(c, cfg, Assets.getField(c, "GREIFING"), category);
               
               category = CATEGORY_DAMAGE_MODIFIER;
               cfg.addCustomCategoryComment(category, DeltaCore.config.buildFancyComment(COMMENT_DAMAGE_MODIFIER, cfg.NEW_LINE, 20));
               DeltaCore.config.getBoolean(c, cfg, Assets.getField(c, "DAMAGE_MODIFIER_ENABLE"), category);
               DeltaCore.config.getInteger(c, cfg, Assets.getField(c, "DAMAGE_MODIFIER_WEAK"), category);
               DeltaCore.config.getInteger(c, cfg, Assets.getField(c, "DAMAGE_MODIFIER_RESIST"), category);
               DeltaCore.config.getInteger(c, cfg, Assets.getField(c, "DAMAGE_MODIFIER_GOLD"), category);
               DeltaCore.config.getInteger(c, cfg, Assets.getField(c, "DAMAGE_MODIFIER_MACE"), category);
               DeltaCore.config.getInteger(c, cfg, Assets.getField(c, "DAMAGE_MODIFIER_STRIKE"), category);
          }
          catch (Exception e)
          {
               e.printStackTrace();
          }
          
          cfg.save();
          
          DeltaCore.config.formatFile(cfg, DESCRIPTION_TUXWEAPONS);
     }
}
