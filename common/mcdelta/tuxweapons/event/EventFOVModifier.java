package mcdelta.tuxweapons.event;

import mcdelta.core.item.ItemDeltaBow;
import mcdelta.tuxweapons.item.ItemGrappHook;
import mcdelta.tuxweapons.item.ItemHammer;
import mcdelta.tuxweapons.item.ItemSpear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventFOVModifier
{
     @ForgeSubscribe
     @SideOnly (Side.CLIENT)
     public void fovUpdate (final FOVUpdateEvent event)
     {
          final EntityPlayer player = event.entity;
          
          if (player.isUsingItem() && (player.getItemInUse().getItem() instanceof ItemDeltaBow || player.getItemInUse().getItem() instanceof ItemSpear || player.getItemInUse().getItem() instanceof ItemHammer || player.getItemInUse().getItem() instanceof ItemGrappHook))
          {
               final int i = player.getItemInUseDuration();
               float f1 = i / 20.0F;
               
               if (f1 > 1.0F)
               {
                    f1 = 1.0F;
               }
               else
               {
                    f1 *= f1;
               }
               
               event.newfov *= 1.0F - f1 * 0.15F;
          }
     }
}
