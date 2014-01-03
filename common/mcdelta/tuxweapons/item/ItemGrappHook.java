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
     private final String toolName;
     public ItemMaterial  itemMaterial;
     
     @SideOnly (Side.CLIENT)
     protected Icon       itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon       overrideIcon;
     
     private boolean      overrideExists = false;
     
     
     
     
     public ItemGrappHook (final ItemMaterial mat)
     {
          super(TuxWeapons.instance, mat.name() + "." + "grappHook", false);
          
          this.toolName = "grappHook";
          this.itemMaterial = mat;
          this.maxStackSize = 1;
          this.setCreativeTab(CreativeTabs.tabTools);
          
          this.setMaxDamage(mat.maxUses());
          
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
     public EnumAction getItemUseAction (final ItemStack stack)
     {
          return EnumAction.bow;
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (final ItemStack stack, final World world, final EntityPlayer player)
     {
          final PlayerData data = new PlayerData(player);
          
          if (data.tag.getInteger(TWNBTTags.GRAPP) == -1)
          {
               final ArrowNockEvent event = new ArrowNockEvent(player, stack);
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
               final EntityGrappHook grappHook = (EntityGrappHook) world.getEntityByID(data.tag.getInteger(TWNBTTags.GRAPP));
               
               if (grappHook != null)
               {
                    if (!player.onGround && grappHook.inGround)
                    {
                         stack.damageItem((int) Math.sqrt(Math.pow(Math.abs(grappHook.posX - player.posX), 2) + Math.pow(Math.abs(grappHook.posY - player.posY), 2) + Math.pow(Math.abs(grappHook.posZ - player.posZ), 2)) / 2, player);
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
               
               final int range = 40;
               final List<EntityGrappHook> hooks = world.getEntitiesWithinAABB(EntityGrappHook.class, AxisAlignedBB.getBoundingBox(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range));
               
               for (final EntityGrappHook grappHook : hooks)
               {
                    if (grappHook.owner.equals(player))
                    {
                         if (!player.onGround && grappHook.inGround)
                         {
                              final double relPosX = grappHook.posX - player.posX;
                              final double relPosY = grappHook.posY - player.posY;
                              final double relPosZ = grappHook.posZ - player.posZ;
                              
                              player.addVelocity(relPosX / 10, (relPosY + 3.5) / 10, relPosZ / 10);
                              
                              stack.damageItem((int) Math.sqrt(Math.pow(Math.abs(grappHook.posX - player.posX), 2) + Math.pow(Math.abs(grappHook.posY - player.posY), 2) + Math.pow(Math.abs(grappHook.posZ - player.posZ), 2)) / 2, player);
                         }
                    }
               }
          }
          
          return stack;
     }
     
     
     
     
     @Override
     public void onPlayerStoppedUsing (final ItemStack stack, final World world, final EntityPlayer player, final int par4)
     {
          final int duration = this.getMaxItemUseDuration(stack) - par4;
          
          float charge = duration / 30.0F;
          
          if (charge <= 0.1F)
          {
               return;
          }
          
          if (charge > 0.8F)
          {
               charge = 0.8F;
          }
          
          final EntityGrappHook hookEntity = new EntityGrappHook(world, player, charge, stack);
          hookEntity.canBePickedUp = 0;
          
          world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.1F - 1.2F) + charge * 0.5F);
          
          if (Assets.isServer())
          {
               world.spawnEntityInWorld(hookEntity);
          }
          
          player.swingItem();
     }
     
     
     
     
     @Override
     public int getMaxItemUseDuration (final ItemStack par1ItemStack)
     {
          return 72000;
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
               return 0xc4c3ac;
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
          
          return material + " " + weapon;
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
}
