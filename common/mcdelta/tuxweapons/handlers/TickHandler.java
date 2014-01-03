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
     public void tickStart (EnumSet<TickType> type, Object... tickData)
     {
          if (type.equals(EnumSet.of(TickType.PLAYER)))
          {
               playerTick(true, (EntityPlayer) tickData[0]);
          }
     }
     
     
     
     
     @Override
     public void tickEnd (EnumSet<TickType> type, Object... tickData)
     {
          if (type.equals(EnumSet.of(TickType.PLAYER)))
          {
               playerTick(false, (EntityPlayer) tickData[0]);
          }
     }
     
     
     
     
     private void playerTick (boolean pre, EntityPlayer player)
     {
          if (player.worldObj.isRemote)
          {
               playerClientTick(player, pre);
          }
     }
     
     
     
     
     private void playerClientTick (EntityPlayer player, boolean pre)
     {
          GameSettings settings = Minecraft.getMinecraft().gameSettings;
          
          if (back == null)
          {
               forward = settings.keyBindForward;
               back = settings.keyBindBack;
               
               left = settings.keyBindLeft;
               right = settings.keyBindRight;
               
               jump = settings.keyBindJump;
               sneak = settings.keyBindSneak;
               
               key = new KeyBinding("nope", 0);
          }
          
          if (player.isPotionActive(PotionTW.confusion.id) && settings.keyBindForward != back)
          {
               settings.keyBindForward = back;
               settings.keyBindBack = forward;
               
               settings.keyBindLeft = right;
               settings.keyBindRight = left;
               
               settings.keyBindJump = sneak;
               settings.keyBindSneak = jump;
          }
          
          if (!player.isPotionActive(PotionTW.confusion.id) && settings.keyBindForward == back)
          {
               settings.keyBindForward = forward;
               settings.keyBindBack = back;
               
               settings.keyBindLeft = left;
               settings.keyBindRight = right;
               
               settings.keyBindJump = jump;
               settings.keyBindSneak = sneak;
          }
          
          if (player.isPotionActive(PotionTW.paralysis.id) && settings.keyBindForward != key)
          {
               settings.keyBindForward = key;
               settings.keyBindBack = key;
               
               settings.keyBindLeft = key;
               settings.keyBindRight = key;
               
               settings.keyBindJump = key;
               settings.keyBindSneak = key;
          }
          
          if (!player.isPotionActive(PotionTW.paralysis.id) && settings.keyBindForward == key)
          {
               settings.keyBindForward = forward;
               settings.keyBindBack = back;
               
               settings.keyBindLeft = left;
               settings.keyBindRight = right;
               
               settings.keyBindJump = jump;
               settings.keyBindSneak = sneak;
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
