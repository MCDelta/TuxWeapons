package mcdelta.tuxweapons.item;

import mcdelta.core.assets.Assets;
import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ItemMaterial;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.entity.EntitySpear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpear extends ItemWeapon
{
     
     public ItemSpear (final ItemMaterial mat)
     {
          super("spear", TuxWeapons.instance, mat, 2.0F);
          this.setMaxDamage((int) (mat.maxUses() * 0.9F));
     }
     
     
     
     
     @Override
     public EnumAction getItemUseAction (final ItemStack stack)
     {
          return EnumAction.bow;
     }
     
     
     
     
     @Override
     public void onPlayerStoppedUsing (final ItemStack stack, final World world, final EntityPlayer player, final int par4)
     {
          stack.damageItem(2, player);
          
          final int duration = this.getMaxItemUseDuration(stack) - par4;
          
          float charge = duration / 30.0F;
          
          if (charge < 0.1D)
          {
               return;
          }
          
          if (charge > 0.8F)
          {
               charge = 0.8F;
          }
          
          final EntitySpear spear = new EntitySpear(world, player, charge, stack.copy());
          
          if (charge == 1.0F)
          {
               spear.setIsCritical(true);
          }
          
          world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.1F - 1.2F) + charge * 0.5F);
          
          if (!player.capabilities.isCreativeMode)
          {
               Assets.clearCurrentItem(player);
          }
          
          if (Assets.isServer())
          {
               world.spawnEntityInWorld(spear);
          }
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (final ItemStack stack, final World world, final EntityPlayer player)
     {
          if (player.capabilities.isCreativeMode || player.inventory.hasItem(this.itemID))
          {
               return super.onItemRightClick(stack, world, player);
          }
          
          return stack;
     }
}
