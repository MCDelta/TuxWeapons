package mcdelta.tuxweapons.item;

import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.item.ItemDelta;
import mcdelta.core.material.ItemMaterial;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.data.PlayerData;
import mcdelta.tuxweapons.data.TWNBTTags;
import mcdelta.tuxweapons.entity.EntityGrappHook;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGrappHook extends ItemDelta implements IExtraPasses
{
     private String      toolName;
     public ItemMaterial itemMaterial;
     
     @SideOnly (Side.CLIENT)
     protected Icon      itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon      overrideIcon;
     
     private boolean     overrideExists = false;
     
     
     
     
     public ItemGrappHook (ItemMaterial mat)
     {
          super(TuxWeapons.instance, mat.getName() + "." + "grappHook", false);
          
          this.toolName = "grappHook";
          this.itemMaterial = mat;
          this.maxStackSize = 1;
          this.setCreativeTab(CreativeTabs.tabTools);
          
          this.setMaxDamage(mat.getMaxUses());
          
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
     public EnumAction getItemUseAction (ItemStack stack)
     {
          return EnumAction.bow;
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
     {
          PlayerData data = new PlayerData(player);
          
          if (data.tag.getInteger(TWNBTTags.GRAPP) == -1)
          {
               ArrowNockEvent event = new ArrowNockEvent(player, stack);
               MinecraftForge.EVENT_BUS.post(event);
               if (event.isCanceled())
               {
                    return event.result;
               }
               
               player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
               
               return stack;
          }
          
          player.swingItem();
          
          if (world.getEntityByID(data.tag.getInteger(TWNBTTags.GRAPP)) instanceof EntityGrappHook)
          {
               EntityGrappHook grappHook = (EntityGrappHook) world.getEntityByID(data.tag.getInteger(TWNBTTags.GRAPP));
               
               if (grappHook != null)
               {
                    if (!player.onGround && grappHook.inGround)
                    {
                         stack.damageItem((int) (Math.sqrt(Math.pow((Math.abs(grappHook.posX - player.posX)), 2) + Math.pow((Math.abs(grappHook.posY - player.posY)), 2) + Math.pow((Math.abs(grappHook.posZ - player.posZ)), 2))) / 2, player);
                    }
                    
                    data.tag.setInteger(TWNBTTags.GRAPP, -1);
                    data.save();
                    
                    grappHook.setDead();
               }
          }
          
          else
          {
               data.tag.setInteger(TWNBTTags.GRAPP, -1);
               data.save();
          }
          
          if (Assets.isClient())
          {
               if (player.getBoundingBox() == null)
               {
                    //return stack;
               }
               
               int range = 40;
               List<EntityGrappHook> hooks = world.getEntitiesWithinAABB(EntityGrappHook.class, AxisAlignedBB.getBoundingBox(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range));
               
               for (EntityGrappHook grappHook : hooks)
               {
                    if (grappHook.owner.equals(player))
                    {
                         if (!player.onGround && grappHook.inGround)
                         {
                              double relPosX = grappHook.posX - player.posX;
                              double relPosY = grappHook.posY - player.posY;
                              double relPosZ = grappHook.posZ - player.posZ;
                              
                              player.addVelocity(relPosX / 10, (relPosY + 3.5) / 10, relPosZ / 10);
                              
                              stack.damageItem((int) (Math.sqrt(Math.pow((Math.abs(grappHook.posX - player.posX)), 2) + Math.pow((Math.abs(grappHook.posY - player.posY)), 2) + Math.pow((Math.abs(grappHook.posZ - player.posZ)), 2))) / 2, player);
                         }
                    }
               }
          }
          
          return stack;
     }
     
     
     
     
     @Override
     public void onPlayerStoppedUsing (ItemStack stack, World world, EntityPlayer player, int par4)
     {
          int duration = this.getMaxItemUseDuration(stack) - par4;
          
          float charge = duration / 30.0F;
          
          if (charge <= 0.1F)
          {
               return;
          }
          
          if (charge > 0.8F)
          {
               charge = 0.8F;
          }
          
          EntityGrappHook hookEntity = new EntityGrappHook(world, player, charge, stack);
          hookEntity.canBePickedUp = 0;
          
          world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.1F - 1.2F) + charge * 0.5F);
          
          if (Assets.isServer())
          {
               world.spawnEntityInWorld(hookEntity);
          }
          
          player.swingItem();
     }
     
     
     
     
     public int getMaxItemUseDuration (ItemStack par1ItemStack)
     {
          return 72000;
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
               return 0xc4c3ac;
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
     
     
     
     
     public boolean getIsRepairable (ItemStack repair, ItemStack gem)
     {
          if (OreDictionary.getOres(itemMaterial.getOreDictionaryName()) != null && !OreDictionary.getOres(itemMaterial.getOreDictionaryName()).isEmpty())
          {
               return OreDictionary.itemMatches(OreDictionary.getOres(itemMaterial.getOreDictionaryName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
          }
          
          return super.getIsRepairable(repair, gem);
     }
}
