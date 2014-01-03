package mcdelta.tuxweapons.item;

import mcdelta.core.DeltaCore;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.item.ItemDelta;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ItemMaterial;
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
<<<<<<< HEAD
     private String      toolName;
     public ToolMaterial toolMaterialDelta;
     
     @SideOnly (Side.CLIENT)
     protected Icon      itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon      overrideIcon;
     
     private boolean     overrideExists = false;
     
     
     
     
     public ItemKnife (ToolMaterial mat)
     {
          super(TuxWeapons.instance, mat.getName() + "." + "knife", false);
          
          this.toolName = "knife";
          this.toolMaterialDelta = mat;
          this.maxStackSize = 64;
          this.setCreativeTab(CreativeTabs.tabCombat);
          
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
     
     
     
     
     @Override
     public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
     {
          player.swingItem();
          
          ItemStack item = stack.copy();
          item.stackSize = 1;
          
          EntityKnife knife = new EntityKnife(world, player, 0.8F, item.copy());
          
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
     public void registerIcons (IconRegister register)
     {
          this.itemIcon = ItemDelta.doRegister(mod.id().toLowerCase(), toolName + "_1", register);
          this.itemOverlay = ItemDelta.doRegister(mod.id().toLowerCase(), toolName + "_2", register);
          
          overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + toolMaterialDelta.getName().toLowerCase() + "_" + toolName + ".png"));
          
          if (overrideExists)
          {
               this.overrideIcon = ItemDelta.doRegister(mod.id().toLowerCase(), "override/" + toolMaterialDelta.getName().toLowerCase() + "_" + toolName, register);
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
               return DeltaCore.WOOD.getColor();
          }
          
          return toolMaterialDelta.getColor();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (ItemStack stack, int pass)
     {
          if (pass == 1 && toolMaterialDelta.isShinyDefault())
          {
               return true;
          }
          
          return false;
     }
     
     
     
     
     public String getItemDisplayName (ItemStack stack)
     {
          ToolMaterial mat = toolMaterialDelta;
          
          String weapon = StatCollector.translateToLocal("tool." + toolName);
          String material = StatCollector.translateToLocal("material." + mat.getName());
          
          return material + " " + weapon;
     }
=======
    private final String toolName;
    public ItemMaterial toolMaterialDelta;

    @SideOnly(Side.CLIENT)
    protected Icon itemOverlay;

    @SideOnly(Side.CLIENT)
    protected Icon overrideIcon;

    private boolean overrideExists = false;

    public ItemKnife(ItemMaterial mat)
    {
        super(TuxWeapons.instance, mat.getName() + "." + "knife", false);

        toolName = "knife";
        toolMaterialDelta = mat;
        maxStackSize = 64;
        setCreativeTab(CreativeTabs.tabCombat);

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

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        player.swingItem();

        ItemStack item = stack.copy();
        item.stackSize = 1;

        EntityKnife knife = new EntityKnife(world, player, 0.8F, item.copy());

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
    public void registerIcons(IconRegister register)
    {
        itemIcon = ItemDelta.doRegister(mod.id().toLowerCase(), toolName + "_1", register);
        itemOverlay = ItemDelta.doRegister(mod.id().toLowerCase(), toolName + "_2", register);

        overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + toolMaterialDelta.getName().toLowerCase() + "_" + toolName
                + ".png"));

        if (overrideExists)
        {
            overrideIcon = ItemDelta.doRegister(mod.id().toLowerCase(), "override/" + toolMaterialDelta.getName().toLowerCase() + "_" + toolName, register);
        }
    }

    @Override
    public int getPasses(ItemStack stack)
    {
        if (overrideExists)
        {
            return 1;
        }

        return 2;
    }

    @Override
    public Icon getIconFromPass(ItemStack stack, int pass)
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
    public int getColorFromPass(ItemStack stack, int pass)
    {
        if (overrideExists)
        {
            return 0xffffff;
        }

        if (pass == 2)
        {
            return MaterialRegistry.WOOD.getColor();
        }

        return toolMaterialDelta.getColor();
    }

    @Override
    public boolean getShinyFromPass(ItemStack stack, int pass)
    {
        if ((pass == 1) && toolMaterialDelta.isShinyDefault())
        {
            return true;
        }

        return false;
    }

    @Override
    public String getItemDisplayName(ItemStack stack)
    {
        ItemMaterial mat = toolMaterialDelta;

        String weapon = StatCollector.translateToLocal("tool." + toolName);
        String material = StatCollector.translateToLocal("material." + mat.getName());

        return material + " " + weapon;
    }
>>>>>>> c1b58e0560f97fde056081d0f4a4f78fdd89f4c4
}
