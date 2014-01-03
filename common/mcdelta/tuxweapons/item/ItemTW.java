package mcdelta.tuxweapons.item;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.item.ItemDelta;
import mcdelta.core.material.ToolMaterial;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.creativetab.CreativeTabs;

public class ItemTW extends ItemDelta
{
     public static ItemFireChargeCannon                fireChargeCannon = new ItemFireChargeCannon();
     public static ItemCrossbow                        crossBow         = new ItemCrossbow();
     public static ItemBolt                            bolt             = new ItemBolt();
     public static ItemDynamite                        dynamite         = new ItemDynamite();
     public static ItemEMPGrenade                      empGrenade       = new ItemEMPGrenade();
     public static ItemTechnical                       technical        = new ItemTechnical();
     public static ItemTW                              magmaCore        = (ItemTW) new ItemTW("magmaCore").setCreativeTab(CreativeTabs.tabMaterials);
     
     public static Map<ToolMaterial, ItemBattleaxe>    battleaxes       = new HashMap();
     public static Map<ToolMaterial, ItemHammer>       hammers          = new HashMap();
     public static Map<ToolMaterial, ItemSpear>        spears           = new HashMap();
     public static Map<ToolMaterial, ItemMace>         maces            = new HashMap();
     public static Map<ToolMaterial, ItemKnife>        knives           = new HashMap();
     public static Map<ToolMaterial, ItemGrappHook>    grappHooks       = new HashMap();
     public static Map<ToolMaterial, ItemShield>       shields          = new HashMap();
     public static Map<ToolMaterial, ItemWhock>        whocks           = new HashMap();
     public static Map<ToolMaterial, ItemWhockCrafter> whockCrafters    = new HashMap();
     
     static
     {
          for (ToolMaterial mat : ToolMaterial.mats)
          {
               if (mat.needsWeapons())
               {
                    ItemBattleaxe battleaxe = new ItemBattleaxe(mat);
                    battleaxes.put(mat, battleaxe);
                    
                    ItemMace mace = new ItemMace(mat);
                    maces.put(mat, mace);
                    
                    ItemHammer hammer = new ItemHammer(mat);
                    hammers.put(mat, hammer);
                    
                    ItemKnife knife = new ItemKnife(mat);
                    knives.put(mat, knife);
                    
                    ItemSpear spear = new ItemSpear(mat);
                    spears.put(mat, spear);
                    
                    ItemGrappHook grappHook = new ItemGrappHook(mat);
                    grappHooks.put(mat, grappHook);
                    
                    ItemShield shield = new ItemShield(mat);
                    shields.put(mat, shield);
                    
                    ItemWhock whock = new ItemWhock(mat);
                    whocks.put(mat, whock);
                    
                    ItemWhockCrafter whockCrafter = new ItemWhockCrafter(mat);
                    whockCrafters.put(mat, whockCrafter);
               }
          }
     }
     
     
     
     
     public ItemTW (String s, boolean b)
     {
          super(TuxWeapons.instance, s, b);
          this.setCreativeTab(CreativeTabs.tabCombat);
     }
     
     
     
     
     public ItemTW (String s)
     {
          super(TuxWeapons.instance, s);
          this.setCreativeTab(CreativeTabs.tabCombat);
     }
     
     
     
     
     public static void loadItems ()
     {
     }
}
