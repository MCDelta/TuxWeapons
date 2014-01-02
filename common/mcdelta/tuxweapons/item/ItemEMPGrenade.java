package mcdelta.tuxweapons.item;

import mcdelta.core.assets.Assets;
import mcdelta.tuxweapons.entity.EntityEMPGrenade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEMPGrenade extends ItemTW
{
     public ItemEMPGrenade ()
     {
          super("empGrenade");
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
     {
          EntityEMPGrenade grenade = new EntityEMPGrenade(world, player);
          
          if (Assets.isServer())
               world.spawnEntityInWorld(grenade);
          
          player.inventory.consumeInventoryItem(this.itemID);
          
          return stack;
     }
}
