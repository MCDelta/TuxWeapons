package mcdelta.tuxweapons.item;

import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.item.ItemDelta;
import mcdelta.core.item.ItemDeltaPickaxe;
import mcdelta.core.material.ItemMaterial;
import mcdelta.tuxweapons.TWContent;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWhockCrafter extends ItemDelta implements IExtraPasses
{
     private final String toolName;
     public ItemMaterial  itemMaterial;
     
     @SideOnly (Side.CLIENT)
     protected Icon       overrideIcon;
     
     private boolean      overrideExists = false;
     
     private final int    spawnDamage;
     
     
     
     
     public ItemWhockCrafter (final ItemMaterial mat)
     {
          super(TuxWeapons.instance, mat.name() + ".whockCrafter", false);
          
          toolName = "whockCrafter";
          itemMaterial = mat;
          setCreativeTab(CreativeTabs.tabTools);
          setMaxStackSize(1);
          setMaxDamage(mat.maxUses());
          spawnDamage = mat.maxUses() - 1;
          
          final String weapon = "tool." + toolName;
          final String material = "material." + mat.name();
          
          if (!StatCollector.func_94522_b(weapon))
          {
               DeltaCore.localizationWarnings.append("- " + weapon + " \n");
          }
          
          if (!StatCollector.func_94522_b(material))
          {
               DeltaCore.localizationWarnings.append("- " + material + " \n");
          }
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     @Override
     public void getSubItems (final int id, final CreativeTabs tab, final List list)
     {
          final ItemStack stack = new ItemStack(id, 1, spawnDamage);
          if (itemMaterial.toolEnchant() != null)
          {
               stack.addEnchantment(itemMaterial.toolEnchant(), itemMaterial.toolEnchantLvl());
          }
          list.add(stack);
     }
     
     
     
     
     @Override
     public boolean getIsRepairable (final ItemStack stack1, final ItemStack stack2)
     {
          if (stack2.getItem() instanceof ItemDeltaPickaxe && ((ItemDeltaPickaxe) stack2.getItem()).itemMaterial.equals(itemMaterial))
          {
               return true;
          }
          
          if (stack2.getItem() instanceof ItemPickaxe && ((ItemPickaxe) stack2.getItem()).getToolMaterialName().toLowerCase().equals(itemMaterial.name().toLowerCase()))
          {
               return true;
          }
          
          return false;
     }
     
     
     
     
     @Override
     public void onUpdate (final ItemStack stack, final World world, final Entity entity, final int i2, final boolean b)
     {
          if (stack.getItemDamage() != spawnDamage && entity instanceof EntityPlayer)
          {
               final EntityPlayer player = (EntityPlayer) entity;
               
               int i = -1;
               
               for (final ItemStack stack2 : player.inventory.mainInventory)
               {
                    i++;
                    
                    if (stack2 != null && stack2.equals(stack))
                    {
                         player.inventory.mainInventory[i] = new ItemStack(TWContent.whocks.get(itemMaterial), 1, stack.getItemDamage());
                         
                         if (stack.hasDisplayName())
                         {
                              player.inventory.mainInventory[i].setItemName(stack.getDisplayName());
                         }
                         
                         if (stack.isItemEnchanted())
                         {
                              final NBTTagList nbttaglist = stack.getEnchantmentTagList();
                              
                              if (nbttaglist != null)
                              {
                                   for (int i3 = 0; i3 < nbttaglist.tagCount(); i3++)
                                   {
                                        player.inventory.mainInventory[i].addEnchantment(Enchantment.enchantmentsList[((NBTTagCompound) nbttaglist.tagAt(i3)).getShort("id")], ((NBTTagCompound) nbttaglist.tagAt(i3)).getShort("lvl"));
                                   }
                              }
                         }
                         
                         player.inventory.inventoryChanged = true;
                    }
               }
          }
     }
     
     
     
     
     @Override
     public boolean isDamaged (final ItemStack stack)
     {
          if (Assets.isClient() && !(stack.getItemDamage() != spawnDamage))
          {
               return false;
          }
          
          return stack.getItemDamage() > 0;
     }
     
     
     
     
     @Override
     public String getItemDisplayName (final ItemStack stack)
     {
          final ItemMaterial mat = itemMaterial;
          
          
          String weapon = StatCollector.translateToLocal("tool." + toolName);
          final String material = StatCollector.translateToLocal("material." + mat.name());
          
          if (stack.getItemDamage() != spawnDamage)
          {
               weapon = StatCollector.translateToLocal("tool.whock");
          }
          
          return mat.getNameColor() + String.format(weapon, material);
     }
     
     
     
     
     @Override
     public void registerIcons (final IconRegister register)
     {
          itemIcon = ItemDelta.doRegister(mod.id().toLowerCase(), toolName, register);
          
          overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + itemMaterial.name().toLowerCase() + "_" + toolName + ".png"));
          
          if (overrideExists)
          {
               overrideIcon = ItemDelta.doRegister(mod.id().toLowerCase(), "override/" + itemMaterial.name().toLowerCase() + "_" + toolName, register);
          }
     }
     
     
     
     
     @Override
     public int getPasses (final ItemStack stack)
     {
          if (stack.getItemDamage() != spawnDamage)
          {
               return TWContent.whocks.get(itemMaterial).getPasses(stack);
          }
          
          return 1;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (final ItemStack stack, final int pass)
     {
          if (stack.getItemDamage() != spawnDamage)
          {
               return TWContent.whocks.get(itemMaterial).getIconFromPass(stack, pass);
          }
          
          if (overrideExists)
          {
               return overrideIcon;
          }
          
          return itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (final ItemStack stack, final int pass)
     {
          if (stack.getItemDamage() != spawnDamage)
          {
               return TWContent.whocks.get(itemMaterial).getColorFromPass(stack, pass);
          }
          
          if (overrideExists)
          {
               return 0xffffff;
          }
          
          return itemMaterial.color();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          if (stack.getItemDamage() != spawnDamage)
          {
               return TWContent.whocks.get(itemMaterial).getShinyFromPass(stack, pass);
          }
          
          if (itemMaterial.defaultShiny())
          {
               return true;
          }
          
          return false;
     }
}
