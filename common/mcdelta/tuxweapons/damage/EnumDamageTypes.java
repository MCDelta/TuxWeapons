package mcdelta.tuxweapons.damage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mcdelta.core.item.ItemDelta;
import mcdelta.core.material.ToolMaterial;
import mcdelta.tuxweapons.TWContent;
import net.minecraft.item.Item;

public enum EnumDamageTypes
{
     SLASHER (new String[]
     { "spider", "cavespider", "zombie", "pigzombie", "slime", "witch" }),
     BASHER (new String[]
     { "ghast", "blaze", "creeper", "skeleton", "silverfish", "lavaslime" }),
     GOLDEN (new String[]
     { "enderman" });
     
     public List<Item>   effc_item   = new ArrayList<Item>();
     public List<String> effc_entity = new ArrayList<String>();
     
     
     static
     {
          for (ToolMaterial mat : ToolMaterial.mats)
          {
               SLASHER.effc_item.add(ItemDelta.swords.get(mat));
               SLASHER.effc_item.add(TWContent.battleaxes.get(mat));
               SLASHER.effc_item.add(TWContent.spears.get(mat));
               
               BASHER.effc_item.add(ItemDelta.axes.get(mat));
               BASHER.effc_item.add(TWContent.hammers.get(mat));
               BASHER.effc_item.add(TWContent.maces.get(mat));
          }
     }
     
     
     
     
     EnumDamageTypes ()
     {
          
     }
     
     
     
     
     EnumDamageTypes (String[] arr)
     {
          this();
          effc_entity.addAll(Arrays.asList(arr));
     }
}
