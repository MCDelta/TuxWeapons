package mcdelta.tuxweapons.handlers;

import java.util.EnumSet;

import mcdelta.tuxweapons.potions.PotionTW;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler
{
     KeyBinding forward;
     KeyBinding back;
     
     KeyBinding left;
     KeyBinding right;
     
     KeyBinding jump;
     KeyBinding sneak;
     
     KeyBinding key;
     
     
     
     
     @Override
     public void tickStart (final EnumSet<TickType> type, final Object... tickData)
     {
          if (type.equals(EnumSet.of(TickType.PLAYER)))
          {
               this.playerTick(true, (EntityPlayer) tickData[0]);
          }
     }
     
     
     
     
     @Override
     public void tickEnd (final EnumSet<TickType> type, final Object... tickData)
     {
          if (type.equals(EnumSet.of(TickType.PLAYER)))
          {
               this.playerTick(false, (EntityPlayer) tickData[0]);
          }
     }
     
     
     
     
     private void playerTick (final boolean pre, final EntityPlayer player)
     {
          if (player.worldObj.isRemote)
          {
               this.playerClientTick(player, pre);
          }
     }
     
     
     
     
     private void playerClientTick (final EntityPlayer player, final boolean pre)
     {
          final GameSettings settings = Minecraft.getMinecraft().gameSettings;
          
          if (this.back == null)
          {
               this.forward = settings.keyBindForward;
               this.back = settings.keyBindBack;
               
               this.left = settings.keyBindLeft;
               this.right = settings.keyBindRight;
               
               this.jump = settings.keyBindJump;
               this.sneak = settings.keyBindSneak;
               
               this.key = new KeyBinding("nope", 0);
          }
          
          if (player.isPotionActive(PotionTW.confusion.id) && settings.keyBindForward != this.back)
          {
               settings.keyBindForward = this.back;
               settings.keyBindBack = this.forward;
               
               settings.keyBindLeft = this.right;
               settings.keyBindRight = this.left;
               
               settings.keyBindJump = this.sneak;
               settings.keyBindSneak = this.jump;
          }
          
          if (!player.isPotionActive(PotionTW.confusion.id) && settings.keyBindForward == this.back)
          {
               settings.keyBindForward = this.forward;
               settings.keyBindBack = this.back;
               
               settings.keyBindLeft = this.left;
               settings.keyBindRight = this.right;
               
               settings.keyBindJump = this.jump;
               settings.keyBindSneak = this.sneak;
          }
          
          if (player.isPotionActive(PotionTW.paralysis.id) && settings.keyBindForward != this.key)
          {
               settings.keyBindForward = this.key;
               settings.keyBindBack = this.key;
               
               settings.keyBindLeft = this.key;
               settings.keyBindRight = this.key;
               
               settings.keyBindJump = this.key;
               settings.keyBindSneak = this.key;
          }
          
          if (!player.isPotionActive(PotionTW.paralysis.id) && settings.keyBindForward == this.key)
          {
               settings.keyBindForward = this.forward;
               settings.keyBindBack = this.back;
               
               settings.keyBindLeft = this.left;
               settings.keyBindRight = this.right;
               
               settings.keyBindJump = this.jump;
               settings.keyBindSneak = this.sneak;
          }
          
          if (!player.isPotionActive(PotionTW.quickStep.id) && player.stepHeight == 1.1415F)
          {
               player.stepHeight = 0.5F;
          }
          
          if (player.isPotionActive(PotionTW.quickStep.id) && player.stepHeight < 1.1415F)
          {
               player.stepHeight = 1.1415F;
          }
     }
     
     
     
     
     @Override
     public EnumSet<TickType> ticks ()
     {
          return EnumSet.of(TickType.PLAYER);
     }
     
     
     
     
     @Override
     public String getLabel ()
     {
          return null;
     }
}
