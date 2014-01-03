package mcdelta.tuxweapons.damage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mcdelta.core.DeltaContent;
<<<<<<< HEAD
import mcdelta.core.material.ToolMaterial;
=======
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ItemMaterial;
>>>>>>> c1b58e0560f97fde056081d0f4a4f78fdd89f4c4
import mcdelta.tuxweapons.TWContent;
import net.minecraft.item.Item;

public enum EnumDamageTypes
{
<<<<<<< HEAD
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
               SLASHER.effc_item.add(DeltaContent.swords.get(mat));
               SLASHER.effc_item.add(TWContent.battleaxes.get(mat));
               SLASHER.effc_item.add(TWContent.spears.get(mat));
               
               BASHER.effc_item.add(DeltaContent.axes.get(mat));
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
=======
    SLASHER(new String[]
    { "spider", "cavespider", "zombie", "pigzombie", "slime", "witch" }),
    BASHER(new String[]
    { "ghast", "blaze", "creeper", "skeleton", "silverfish", "lavaslime" }),
    GOLDEN(new String[]
    { "enderman" });

    public List<Item> effc_item = new ArrayList<Item>();
    public List<String> effc_entity = new ArrayList<String>();

    static
    {
        for (ItemMaterial mat : MaterialRegistry.materials())
        {
            SLASHER.effc_item.add(DeltaContent.swords.get(mat));
            SLASHER.effc_item.add(TWContent.battleaxes.get(mat));
            SLASHER.effc_item.add(TWContent.spears.get(mat));

            BASHER.effc_item.add(DeltaContent.axes.get(mat));
            BASHER.effc_item.add(TWContent.hammers.get(mat));
            BASHER.effc_item.add(TWContent.maces.get(mat));
        }
    }

    EnumDamageTypes()
    {

    }

    EnumDamageTypes(String[] arr)
    {
        this();
        effc_entity.addAll(Arrays.asList(arr));
    }
>>>>>>> c1b58e0560f97fde056081d0f4a4f78fdd89f4c4
}
