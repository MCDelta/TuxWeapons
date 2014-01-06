package mcdelta.tuxweapons.item;

import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.item.ItemDelta;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.entity.EntityKnife;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemKnife extends ItemDelta implements IExtraPasses
{
     private final String toolName;
     public ItemMaterial  itemMaterial;
     
     @SideOnly (Side.CLIENT)
     protected Icon       itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon       overrideIcon;
     
     private boolean      overrideExists = false;
     
     
     
     
     public ItemKnife (final ItemMaterial mat)
     {
          super(TuxWeapons.instance, mat.name() + "." + "knife", false);
          
          this.toolName = "knife";
          this.itemMaterial = mat;
          this.maxStackSize = 64;
          this.setCreativeTab(CreativeTabs.tabCombat);
          
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
     
     
     
     
     @Override
     public ItemStack onItemRightClick (ItemStack stack, final World world, final EntityPlayer player)
     {
          player.swingItem();
          
          final ItemStack item = stack.copy();
          item.stackSize = 1;
          
          final EntityKnife knife = new EntityKnife(world, player, 0.8F, item.copy());
          
          if (!player.capabilities.isCreativeMode)
          {
               stack.stackSize -= 1;
               
               if (stack.stackSize <= 0)
               {
                    stack = null;
                    player.destroyCurrentEquippedItem();
               }
          }
          
          if (Assets.isServer())
          {
               world.spawnEntityInWorld(knife);
          }
          
          return stack;
     }
     
     
     
     
     @Override
     public void registerIcons (final IconRegister register)
     {
          this.itemIcon = ItemDelta.doRegister(this.mod.id().toLowerCase(), this.toolName + "_1", register);
          this.itemOverlay = ItemDelta.doRegister(this.mod.id().toLowerCase(), this.toolName + "_2", register);
          
          this.overrideExists = Assets.resourceExists(new ResourceLocation(this.mod.id().toLowerCase(), "textures/items/override/" + this.itemMaterial.name().toLowerCase() + "_" + this.toolName + ".png"));
          
          if (this.overrideExists)
          {
               this.overrideIcon = ItemDelta.doRegister(this.mod.id().toLowerCase(), "override/" + this.itemMaterial.name().toLowerCase() + "_" + this.toolName, register);
          }
     }
     
     
     
     
     @Override
     public int getPasses (final ItemStack stack)
     {
          if (this.overrideExists)
          {
               return 1;
          }
          
          return 2;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (final ItemStack stack, final int pass)
     {
          if (this.overrideExists)
          {
               return this.overrideIcon;
          }
          
          if (pass == 2)
          {
               return this.itemOverlay;
          }
          
          return this.itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (final ItemStack stack, final int pass)
     {
          if (this.overrideExists)
          {
               return 0xffffff;
          }
          
          if (pass == 2)
          {
               if(itemMaterial.nonStickCrafter() != null)
               {
                    return itemMaterial.nonStickCrafter().getValue();
               }
               
               return MaterialRegistry.WOOD.color();
          }
          
          return this.itemMaterial.color();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          if (pass == 1 && this.itemMaterial.defaultShiny())
          {
               return true;
          }
          
          return false;
     }
     
     
     
     
     @Override
     public String getItemDisplayName (final ItemStack stack)
     {
          final ItemMaterial mat = this.itemMaterial;
          
          final String weapon = StatCollector.translateToLocal("tool." + this.toolName);
          final String material = StatCollector.translateToLocal("material." + mat.name());
          
          return mat.getNameColor() + material + " " + weapon;
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void getSubItems (int id, CreativeTabs tab, List list)
     {
          ItemStack stack = new ItemStack(id, 1, 0);
          if (itemMaterial.weaponEnchant() != null)
               stack.addEnchantment(itemMaterial.weaponEnchant(), itemMaterial.weaponEnchantLvl());
          list.add(stack);
     }
}
