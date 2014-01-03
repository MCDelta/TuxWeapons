package mcdelta.tuxweapons.item;

import mcdelta.core.DeltaCore;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.item.ItemDelta;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.proxy.ClientProxy;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.client.item.RenderShield;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemShield extends ItemDelta implements IExtraPasses
{
     private String      toolName;
     public ItemMaterial itemMaterial;
     
     @SideOnly (Side.CLIENT)
     protected Icon      itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon      overrideIcon;
     
     private boolean     overrideExists = false;
     
     
     
     
     public ItemShield (ItemMaterial mat)
     {
          super(TuxWeapons.instance, mat.getName() + "." + "shield", false);
          
          this.toolName = "shield";
          this.itemMaterial = mat;
          this.maxStackSize = 1;
          this.setCreativeTab(CreativeTabs.tabTools);
          
          this.setMaxDamage((int) (mat.getMaxUses() * 0.5F));
          
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
          
          ClientProxy.extraPasses.remove(this);
          
          if (Assets.isClient())
          {
               MinecraftForgeClient.registerItemRenderer(itemID, new RenderShield());
          }
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
     {
          player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
          return stack;
     }
     
     
     
     
     @Override
     public EnumAction getItemUseAction (ItemStack stack)
     {
          return EnumAction.block;
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister register)
     {
          this.itemIcon = ItemDelta.doRegister(mod.id().toLowerCase(), toolName + "_1", register);
          this.itemOverlay = ItemDelta.doRegister(mod.id().toLowerCase(), toolName + "_2", register);
          
          overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + itemMaterial.getName().toLowerCase() + "_" + toolName + ".png"));
          
          if (overrideExists)
          {
               this.overrideIcon = ItemDelta.doRegister(mod.id().toLowerCase(), "override/" + itemMaterial.getName().toLowerCase() + "_" + toolName, register);
          }
     }
     
     
     
     
     @Override
     public int getPasses (ItemStack stack)
     {
          if (overrideExists)
          {
               return 1;
          }
          
          return 2;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (ItemStack stack, int pass)
     {
          if (overrideExists)
          {
               return overrideIcon;
          }
          
          if (pass == 2)
          {
               return itemOverlay;
          }
          
          return itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (ItemStack stack, int pass)
     {
          if (overrideExists)
          {
               return 0xffffff;
          }
          
          if (pass == 2)
          {
               return MaterialRegistry.WOOD.getColor();
          }
          
          return itemMaterial.getColor();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (ItemStack stack, int pass)
     {
          if (pass == 1 && itemMaterial.isShinyDefault())
          {
               return true;
          }
          
          return false;
     }
     
     
     
     
     public String getItemDisplayName (ItemStack stack)
     {
          ItemMaterial mat = itemMaterial;
          
          String weapon = StatCollector.translateToLocal("tool." + toolName);
          String material = StatCollector.translateToLocal("material." + mat.getName());
          
          return material + " " + weapon;
     }
     
     
     
     
     public int getMaxItemUseDuration (ItemStack par1ItemStack)
     {
          return 72000;
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public boolean isFull3D ()
     {
          return true;
     }
     
     
     
     
     public boolean getIsRepairable (ItemStack repair, ItemStack gem)
     {
          if (OreDictionary.getOres(itemMaterial.getOreDictionaryName()) != null && !OreDictionary.getOres(itemMaterial.getOreDictionaryName()).isEmpty())
          {
               return OreDictionary.itemMatches(OreDictionary.getOres(itemMaterial.getOreDictionaryName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
          }
          
          return super.getIsRepairable(repair, gem);
     }
}
