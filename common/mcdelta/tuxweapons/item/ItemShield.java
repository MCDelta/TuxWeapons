package mcdelta.tuxweapons.item;

import java.util.List;

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
     private final String toolName;
     public ItemMaterial  itemMaterial;
     
     @SideOnly (Side.CLIENT)
     protected Icon       itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon       overrideIcon;
     
     private boolean      overrideExists = false;
     
     
     
     
     public ItemShield (final ItemMaterial mat)
     {
          super(TuxWeapons.instance, mat.name() + "." + "shield", false);
          
          this.toolName = "shield";
          this.itemMaterial = mat;
          this.maxStackSize = 1;
          this.setCreativeTab(CreativeTabs.tabTools);
          
          this.setMaxDamage((int) (mat.maxUses() * 0.5F));
          
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
          
          ClientProxy.extraPasses.remove(this);
          
          if (Assets.isClient())
          {
               MinecraftForgeClient.registerItemRenderer(this.itemID, new RenderShield());
          }
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (final ItemStack stack, final World world, final EntityPlayer player)
     {
          player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
          return stack;
     }
     
     
     
     
     @Override
     public EnumAction getItemUseAction (final ItemStack stack)
     {
          return EnumAction.block;
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
     
     
     
     
     @Override
     public int getMaxItemUseDuration (final ItemStack par1ItemStack)
     {
          return 72000;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public boolean isFull3D ()
     {
          return true;
     }
     
     
     
     
     @Override
     public boolean getIsRepairable (final ItemStack repair, final ItemStack gem)
     {
          if (OreDictionary.getOres(this.itemMaterial.oreName()) != null && !OreDictionary.getOres(this.itemMaterial.oreName()).isEmpty())
          {
               return OreDictionary.itemMatches(OreDictionary.getOres(this.itemMaterial.oreName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
          }
          
          return super.getIsRepairable(repair, gem);
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void getSubItems (int id, CreativeTabs tab, List list)
     {
          ItemStack stack = new ItemStack(id, 1, 0);
          if (itemMaterial.toolEnchant() != null)
               stack.addEnchantment(itemMaterial.toolEnchant(), itemMaterial.toolEnchantLvl());
          list.add(stack);
     }
}
