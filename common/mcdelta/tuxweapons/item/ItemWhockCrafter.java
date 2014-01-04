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
          
          this.toolName = "whockCrafter";
          this.itemMaterial = mat;
          this.setCreativeTab(CreativeTabs.tabTools);
          this.setMaxStackSize(1);
          this.setMaxDamage(mat.maxUses());
          this.spawnDamage = mat.maxUses() - 1;
          
          final String weapon = "tool." + this.toolName;
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
          ItemStack stack = new ItemStack(id, 1, this.spawnDamage);
          if (itemMaterial.toolEnchant() != null)
               stack.addEnchantment(itemMaterial.toolEnchant(), itemMaterial.toolEnchantLvl());
          list.add(stack);
     }
     
     
     
     
     @Override
     public boolean getIsRepairable (final ItemStack stack1, final ItemStack stack2)
     {
          if (stack2.getItem() instanceof ItemDeltaPickaxe && ((ItemDeltaPickaxe) stack2.getItem()).itemMaterial.equals(this.itemMaterial))
          {
               return true;
          }
          
          if (stack2.getItem() instanceof ItemPickaxe && ((ItemPickaxe) stack2.getItem()).getToolMaterialName().toLowerCase().equals(this.itemMaterial.name().toLowerCase()))
          {
               return true;
          }
          
          return false;
     }
     
     
     
     
     @Override
     public void onUpdate (final ItemStack stack, final World world, final Entity entity, final int i2, final boolean b)
     {
          if (stack.getItemDamage() != this.spawnDamage && entity instanceof EntityPlayer)
          {
               final EntityPlayer player = (EntityPlayer) entity;
               
               int i = -1;
               
               for (final ItemStack stack2 : player.inventory.mainInventory)
               {
                    i++;
                    
                    if (stack2 != null && stack2.equals(stack))
                    {
                         player.inventory.mainInventory[i] = new ItemStack(TWContent.whocks.get(this.itemMaterial), 1, stack.getItemDamage());
                         
                         if (stack.hasDisplayName())
                         {
                              player.inventory.mainInventory[i].setItemName(stack.getDisplayName());
                         }
                         
                         if (stack.isItemEnchanted())
                         {
                              NBTTagList nbttaglist = stack.getEnchantmentTagList();
                              
                              if (nbttaglist != null)
                              {
                                   for (int i3 = 0; i3 < nbttaglist.tagCount(); i3++)
                                   {
                                        player.inventory.mainInventory[i].addEnchantment(Enchantment.enchantmentsList[((NBTTagCompound)nbttaglist.tagAt(i3)).getShort("id")], ((NBTTagCompound)nbttaglist.tagAt(i3)).getShort("lvl"));
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
          if (Assets.isClient() && !(stack.getItemDamage() != this.spawnDamage))
          {
               return false;
          }
          
          return stack.getItemDamage() > 0;
     }
     
     
     
     
     @Override
     public String getItemDisplayName (final ItemStack stack)
     {
          final ItemMaterial mat = this.itemMaterial;
          
          
          String weapon = StatCollector.translateToLocal("tool." + this.toolName);
          final String material = StatCollector.translateToLocal("material." + mat.name());
          
          if (stack.getItemDamage() != this.spawnDamage)
          {
               weapon = StatCollector.translateToLocal("tool.whock");
          }
          
          return mat.getNameColor() + material + " " + weapon;
     }
     
     
     
     
     @Override
     public void registerIcons (final IconRegister register)
     {
          this.itemIcon = ItemDelta.doRegister(this.mod.id().toLowerCase(), this.toolName, register);
          
          this.overrideExists = Assets.resourceExists(new ResourceLocation(this.mod.id().toLowerCase(), "textures/items/override/" + this.itemMaterial.name().toLowerCase() + "_" + this.toolName + ".png"));
          
          if (this.overrideExists)
          {
               this.overrideIcon = ItemDelta.doRegister(this.mod.id().toLowerCase(), "override/" + this.itemMaterial.name().toLowerCase() + "_" + this.toolName, register);
          }
     }
     
     
     
     
     @Override
     public int getPasses (final ItemStack stack)
     {
          if (stack.getItemDamage() != this.spawnDamage)
          {
               return TWContent.whocks.get(this.itemMaterial).getPasses(stack);
          }
          
          return 1;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (final ItemStack stack, final int pass)
     {
          if (stack.getItemDamage() != this.spawnDamage)
          {
               return TWContent.whocks.get(this.itemMaterial).getIconFromPass(stack, pass);
          }
          
          if (this.overrideExists)
          {
               return this.overrideIcon;
          }
          
          return this.itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (final ItemStack stack, final int pass)
     {
          if (stack.getItemDamage() != this.spawnDamage)
          {
               return TWContent.whocks.get(this.itemMaterial).getColorFromPass(stack, pass);
          }
          
          if (this.overrideExists)
          {
               return 0xffffff;
          }
          
          return this.itemMaterial.color();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          if (stack.getItemDamage() != this.spawnDamage)
          {
               return TWContent.whocks.get(this.itemMaterial).getShinyFromPass(stack, pass);
          }
          
          if (this.itemMaterial.defaultShiny())
          {
               return true;
          }
          
          return false;
     }
}
