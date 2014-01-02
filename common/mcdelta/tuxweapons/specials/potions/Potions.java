package mcdelta.tuxweapons.specials.potions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mcdelta.tuxweapons.TuxWeaponsCore;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

public class Potions
{
     public static Map<Item, Potion>     itemToPotion    = new HashMap();
     public static Map<Potion, Item>     potionToItem    = new HashMap();
     public static Map<Item, String>     modifiers       = new HashMap();
     public static Map<Integer, Integer> corruption      = new HashMap();
     public static List<Potion>          nonUpgradable   = new ArrayList();
     public static List<Potion>          nonInifinite    = new ArrayList();
     public static List<Potion>          badPotions      = new ArrayList();
     public static List<Potion>          creativeTab     = new ArrayList();
     
     public static int                   timeLvl2        = 9600;
     public static int                   time            = 3600;
     public static int                   timeTier2       = 1800;
     public static int                   timeSplashLvl2  = 7200;
     public static int                   timeSplash      = 2700;
     public static int                   timeSplashTier2 = 1340;
     
     
     
     
     public static void init ()
     {
          setupBrewing();
     }
     
     
     
     
     private static void setupBrewing ()
     {
          Item.potion.setPotionEffect("");
          
          addPotion(PotionTW.cure, Item.bucketMilk, Potion.harm.id, true);
          addPotion(PotionTW.fireAura, Item.fireworkCharge, Potion.blindness.id, false);
          addPotion(PotionTW.quickStep, Item.feather, PotionTW.confusion.id, true);
          addPotion(PotionTW.flight, Item.eyeOfEnder, PotionTW.paralysis.id, true);
          nonUpgradable.add(PotionTW.confusion);
          nonUpgradable.add(PotionTW.paralysis);
          
          addPotion(Potion.digSpeed, Item.pumpkinPie, Potion.digSlowdown.id, false);
          creativeTab.add(Potion.digSpeed);
          creativeTab.add(Potion.digSlowdown);
          
          addPotion(Potion.nightVision, Item.goldenCarrot, Potion.invisibility.id, true);
          nonUpgradable.add(Potion.invisibility);
          
          addPotion(Potion.fireResistance, Item.magmaCream, Potion.moveSlowdown.id, true);
          addPotion(Potion.moveSpeed, Item.sugar, Potion.moveSlowdown.id);
          addPotion(Potion.heal, Item.speckledMelon, Potion.harm.id, false);
          
          addPotion(Potion.poison, Item.spiderEye, Potion.harm.id);
          addPotion(Potion.regeneration, Item.ghastTear, Potion.weakness.id, false);
          addPotion(Potion.damageBoost, Item.blazePowder, Potion.weakness.id);
          
          modifiers.put(Item.redstone, "time8/3");
          modifiers.put(Item.gunpowder, "splash");
          modifiers.put(Item.fermentedSpiderEye, "negative");
          modifiers.put(Item.glowstone, "upgrade");
          modifiers.put(Item.netherStar, "infinite");
          
          badPotions.add(Potion.moveSlowdown);
          badPotions.add(Potion.digSlowdown);
          badPotions.add(Potion.harm);
          badPotions.add(Potion.confusion);
          badPotions.add(Potion.blindness);
          badPotions.add(Potion.hunger);
          badPotions.add(Potion.weakness);
          badPotions.add(Potion.poison);
          badPotions.add(Potion.wither);
          badPotions.add(Potion.digSlowdown);
          badPotions.add(PotionTW.confusion);
          badPotions.add(PotionTW.paralysis);
          
          for (Potion potion : badPotions)
          {
               nonInifinite.add(potion);
          }
          
          Item.netherStar.setPotionEffect("");
          Item.potion.setPotionEffect("");
     }
     
     
     
     
     private static void addPotion (Potion potion, Item item, int corruptedID, boolean b2)
     {
          addPotion(potion, item, corruptedID);
          
          if (b2)
          {
               nonUpgradable.add(potion);
          }
          
          if (potion instanceof PotionTW)
          {
               creativeTab.add(potion);
          }
          
          if (Potion.potionTypes[corruptedID] instanceof PotionTW)
          {
               creativeTab.add(Potion.potionTypes[corruptedID]);
          }
     }
     
     
     
     
     private static void addPotion (Potion potion, Item item, int corruptedID)
     {
          item.setPotionEffect("");
          itemToPotion.put(item, potion);
          potionToItem.put(potion, item);
          
          corruption.put(potion.id, corruptedID);
     }
}
