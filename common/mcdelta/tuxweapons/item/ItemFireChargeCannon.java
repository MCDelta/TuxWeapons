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
          setMaxDamage(100);
          maxStackSize = 1;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void registerIcons (final IconRegister register)
     {
          itemIcon = register.registerIcon("tuxweapons:fireChargeCannon");
     }
     
     
     
     
     @Override
     public void onPlayerStoppedUsing (final ItemStack stack, final World world, final EntityPlayer player, final int par4)
     {
          final int charge = getMaxItemUseDuration(stack) - par4;
          
          final EntityTWFireball fireBall = new EntityTWFireball(world, player, charge);
          
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
     public Multimap<String, AttributeModifier> getItemAttributeModifiers ()
     {
          final Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
          multimap.removeAll(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName());
          multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", -0.05D, 0));
          
          return multimap;
     }
     
     
     
     
     @Override
     public int getItemEnchantability ()
     {
          return 0;
     }
}
