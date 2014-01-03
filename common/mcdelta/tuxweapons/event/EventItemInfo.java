package mcdelta.tuxweapons.event;

import static mcdelta.tuxweapons.damage.EnumDamageTypes.BASHER;
import static mcdelta.tuxweapons.damage.EnumDamageTypes.SLASHER;

import java.util.List;

import mcdelta.tuxweapons.config.TWSettings;
import mcdelta.tuxweapons.item.ItemMace;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class EventItemInfo
{
     @ForgeSubscribe
     public void getTooltip (ItemTooltipEvent event)
     {
          ItemStack stack = event.itemStack;
          Item item = event.itemStack.getItem();
          List<String> toolTip = event.toolTip;
          int damageIndex = -1;
          
          for (int i = 0; i < toolTip.size(); i++)
          {
               if (toolTip.get(i).contains(StatCollector.translateToLocal("attribute.name.generic.attackDamage")))
               {
                    damageIndex = i;
               }
               
               if (toolTip.get(i).contains("(200:00)"))
               {
                    String s = toolTip.get(i).replace("(200:00)", "(**:**)");
                    toolTip.set(i, s);
               }
          }
          
          if (damageIndex != -1)
          {
               if (BASHER.effc_item.contains(item))
               {
                    toolTip.add(EnumChatFormatting.RED + "Basher" + EnumChatFormatting.RESET);
               }
               
               if (SLASHER.effc_item.contains(item))
               {
                    toolTip.add(EnumChatFormatting.RED + "Slasher" + EnumChatFormatting.RESET);
               }
               
               if (item instanceof ItemMace)
               {
                    int damage = (int) ((AttributeModifier) stack.getAttributeModifiers().get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName()).toArray()[0]).getAmount();
                    
                    toolTip.set(damageIndex, EnumChatFormatting.BLUE + "+" + damage + "-" + (damage + TWSettings.DAMAGE_MODIFIER_MACE) + " " + StatCollector.translateToLocal("attribute.name.generic.attackDamage") + EnumChatFormatting.RESET);
               }
          }
     }
}
