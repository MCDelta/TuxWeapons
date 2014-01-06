package mcdelta.tuxweapons.item;

import mcdelta.core.assets.Assets;
import mcdelta.tuxweapons.entity.EntityDynamite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDynamite extends ItemTW
{
     
     public ItemDynamite ()
     {
          super("dynamite");
     }
     
     
     
     
     @Override
     public boolean isFull3D ()
     {
          return true;
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (final ItemStack stack, final World world, final EntityPlayer player)
     {
          world.playSoundAtEntity(player, "random.hurt", 1.0F, 1.0F / (itemRand.nextFloat() * 0.1F + 0.95F));
          
          final EntityDynamite dynamite = new EntityDynamite(world, player);
          
          if (Assets.isServer())
          {
               world.spawnEntityInWorld(dynamite);
          }
          
          if (!player.capabilities.isCreativeMode)
          {
               player.inventory.consumeInventoryItem(itemID);
          }
          
          return stack;
     }
}
