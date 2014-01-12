package mcdelta.tuxweapons.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcdelta.tuxweapons.data.TWNBTTags;
import mcdelta.tuxweapons.potions.Potions;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemTechnical extends ItemTW
{
     public ItemTechnical ()
     {
          super("technical");
          setCreativeTab(CreativeTabs.tabBrewing);
     }
     
     
     
     
     @Override
     public void getSubItems (final int id, final CreativeTabs tabs, final List list)
     {
          for (int i = 0; i < Potions.creativeTab.size(); i++)
          {
               final Potion potion = Potions.creativeTab.get(i);
               
               for (int l = 0; l < 6; l++)
               {
                    final ItemStack stack = new ItemStack(Item.potion);
                    final NBTTagList tags = new NBTTagList();
                    boolean splash = false;
                    PotionEffect effect = null;
                    
                    switch (l)
                    {
                         case 0:
                         case 3:
                              effect = new PotionEffect(potion.id, (int) (Potions.time * potion.getEffectiveness()), 0);
                              break;
                         case 1:
                         case 4:
                              if (!potion.isInstant())
                              {
                                   effect = new PotionEffect(potion.id, (int) (Potions.time * potion.getEffectiveness() * (8 / 3)), 0);
                              }
                              break;
                         case 2:
                         case 5:
                              if (!Potions.nonUpgradable.contains(potion))
                              {
                                   effect = new PotionEffect(potion.id, (int) (Potions.time * potion.getEffectiveness() / 2), 1);
                              }
                              break;
                    }
                    ;
                    
                    if (l >= 3)
                    {
                         splash = true;
                    }
                    
                    if (effect != null)
                    {
                         if (potion.isInstant())
                         {
                              effect.duration = 0;
                         }
                         
                         if (splash)
                         {
                              stack.setItemDamage(TWNBTTags.SPLASH_ID);
                              effect.duration /= 2;
                         }
                         
                         else
                         {
                              stack.setItemDamage(16);
                         }
                         
                         tags.appendTag(effect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
                         
                         stack.stackTagCompound = new NBTTagCompound();
                         stack.stackTagCompound.setTag(TWNBTTags.POTION_EFFECTS, tags);
                         
                         list.add(stack);
                    }
               }
          }
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void registerIcons (final IconRegister register)
     {
          
     }
}
