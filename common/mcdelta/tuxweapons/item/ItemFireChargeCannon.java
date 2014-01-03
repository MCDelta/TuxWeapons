package mcdelta.tuxweapons.item;

import mcdelta.core.assets.Assets;
import mcdelta.core.item.ItemDeltaBow;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.entity.EntityTWFireball;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFireChargeCannon extends ItemDeltaBow
{
     public ItemFireChargeCannon ()
     {
          super(TuxWeapons.instance, "fireChargeCannon", new Item[]
          { Item.fireballCharge });
          this.setMaxDamage(100);
          this.maxStackSize = 1;
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void registerIcons (IconRegister register)
     {
          this.itemIcon = register.registerIcon("tuxweapons:fireChargeCannon");
     }
     
     
     
     
     @Override
     public void onPlayerStoppedUsing (ItemStack stack, World world, EntityPlayer player, int par4)
     {
          int charge = this.getMaxItemUseDuration(stack) - par4;
          
          EntityTWFireball fireBall = new EntityTWFireball(world, player, charge);
          
          if (charge >= 20)
          {
               if (Assets.isServer())
               {
                    world.spawnEntityInWorld(fireBall);
               }
               
               stack.damageItem(1, player);
               if (player.capabilities.isCreativeMode == false)
               {
                    player.inventory.consumeInventoryItem(Item.fireballCharge.itemID);
               }
          }
     }
     
     
     
     
     @Override
     public Multimap getItemAttributeModifiers ()
     {
          Multimap multimap = super.getItemAttributeModifiers();
          multimap.removeAll(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName());
          multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", -0.05D, 0));
          
          return multimap;
     }
     
     
     
     
     public int getItemEnchantability ()
     {
          return 0;
     }
}
