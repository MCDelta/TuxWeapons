package mcdelta.tuxweapons.item;

import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.item.ItemDelta;
import mcdelta.core.item.ItemDeltaPickaxe;
import mcdelta.core.material.ToolMaterial;
import mcdelta.tuxweapons.TWContent;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWhockCrafter extends ItemDelta implements IExtraPasses
{
     private String      toolName;
     public ToolMaterial toolMaterial;
     
     @SideOnly (Side.CLIENT)
     protected Icon      overrideIcon;
     
     private boolean     overrideExists = false;
     
     private int         spawnDamage;
     
     
     
     
     public ItemWhockCrafter (ToolMaterial mat)
     {
          super(TuxWeapons.instance, mat.getName() + ".whockCrafter", false);
          
          this.toolName = "whockCrafter";
          this.toolMaterial = mat;
          this.setCreativeTab(CreativeTabs.tabTools);
          this.setMaxStackSize(1);
          this.setMaxDamage(mat.getMaxUses());
          this.spawnDamage = mat.getMaxUses() - 1;
          
          String weapon = "tool." + toolName;
          String material = "material." + mat.getName();
          
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
     public void getSubItems (int id, CreativeTabs tab, List list)
     {
          list.add(new ItemStack(id, 1, spawnDamage));
     }
     
     
     
     
     public boolean getIsRepairable (ItemStack stack1, ItemStack stack2)
     {
          if (stack2.getItem() instanceof ItemDeltaPickaxe && ((ItemDeltaPickaxe) stack2.getItem()).toolMaterial == this.toolMaterial)
          {
               return true;
          }
          
          if (stack2.getItem() instanceof ItemPickaxe && ((ItemPickaxe) stack2.getItem()).getToolMaterialName() == this.toolMaterial.toolMaterial.name())
          {
               return true;
          }
          
          return false;
     }
     
     
     
     
     public void onUpdate (ItemStack stack, World world, Entity entity, int i2, boolean b)
     {
          if (stack.getItemDamage() != spawnDamage && entity instanceof EntityPlayer)
          {
               EntityPlayer player = (EntityPlayer) entity;
               
               int i = -1;
               
               for (ItemStack stack2 : player.inventory.mainInventory)
               {
                    i++;
                    
                    if (stack2 != null && stack2.equals(stack))
                    {
                         player.inventory.mainInventory[i] = new ItemStack(TWContent.whocks.get(toolMaterial), 1, stack.getItemDamage());
                         
                         if (stack.hasDisplayName())
                         {
                              player.inventory.mainInventory[i].setItemName(stack.getDisplayName());
                         }
                         
                         player.inventory.inventoryChanged = true;
                    }
               }
          }
     }
     
     
     
     
     public boolean isDamaged (ItemStack stack)
     {
          if (Assets.isClient() && !(stack.getItemDamage() != spawnDamage))
          {
               return false;
          }
          
          return stack.getItemDamage() > 0;
     }
     
     
     
     
     public String getItemDisplayName (ItemStack stack)
     {
          ToolMaterial mat = toolMaterial;
          
          
          String weapon = StatCollector.translateToLocal("tool." + toolName);
          String material = StatCollector.translateToLocal("material." + mat.getName());
          
          if (stack.getItemDamage() != spawnDamage)
          {
               weapon = StatCollector.translateToLocal("tool.whock");
          }
          
          return material + " " + weapon;
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister register)
     {
          this.itemIcon = ItemDelta.doRegister(mod.id().toLowerCase(), toolName, register);
          
          overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + toolMaterial.getName().toLowerCase() + "_" + toolName + ".png"));
          
          if (overrideExists)
          {
               this.overrideIcon = ItemDelta.doRegister(mod.id().toLowerCase(), "override/" + toolMaterial.getName().toLowerCase() + "_" + toolName, register);
          }
     }
     
     
     
     
     @Override
     public int getPasses (ItemStack stack)
     {
          if (stack.getItemDamage() != spawnDamage)
          {
               return TWContent.whocks.get(toolMaterial).getPasses(stack);
          }
          
          return 1;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (ItemStack stack, int pass)
     {
          if (stack.getItemDamage() != spawnDamage)
          {
               return TWContent.whocks.get(toolMaterial).getIconFromPass(stack, pass);
          }
          
          if (overrideExists)
          {
               return overrideIcon;
          }
          
          return itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (ItemStack stack, int pass)
     {
          if (stack.getItemDamage() != spawnDamage)
          {
               return TWContent.whocks.get(toolMaterial).getColorFromPass(stack, pass);
          }
          
          if (overrideExists)
          {
               return 0xffffff;
          }
          
          return toolMaterial.getColor();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (ItemStack stack, int pass)
     {
          if (stack.getItemDamage() != spawnDamage)
          {
               return TWContent.whocks.get(toolMaterial).getShinyFromPass(stack, pass);
          }
          
          if (toolMaterial.isShinyDefault())
          {
               return true;
          }
          
          return false;
     }
}
