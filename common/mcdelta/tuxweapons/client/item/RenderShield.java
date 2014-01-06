package mcdelta.tuxweapons.client.item;

import java.util.Random;

import mcdelta.core.assets.client.RenderAssets;
import mcdelta.core.client.item.IExtraPasses;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderShield implements IItemRenderer
{
     Random rand   = new Random();
     int    zLevel = 0;
     
     
     
     
     @Override
     public boolean handleRenderType (final ItemStack item, final ItemRenderType type)
     {
          switch (type)
          {
               case INVENTORY:
                    return true;
               case EQUIPPED:
                    return true;
               case EQUIPPED_FIRST_PERSON:
                    return true;
               case ENTITY:
                    return true;
               default:
                    return false;
          }
     }
     
     
     
     
     @Override
     public boolean shouldUseRenderHelper (final ItemRenderType type, final ItemStack item, final ItemRendererHelper helper)
     {
          return helper == ItemRendererHelper.ENTITY_ROTATION && Minecraft.getMinecraft().gameSettings.fancyGraphics || helper == ItemRendererHelper.ENTITY_BOBBING;
     }
     
     
     
     
     @Override
     public void renderItem (final ItemRenderType type, final ItemStack stack, final Object... data)
     {
          GL11.glPushMatrix();
          
          final int passes = ((IExtraPasses) stack.getItem()).getPasses(stack);
          final Icon[] icons = new Icon[passes];
          final int[] colors = new int[passes];
          final boolean[] shiny = new boolean[passes];
          
          for (int i = 0; i < passes; i++)
          {
               icons[i] = ((IExtraPasses) stack.getItem()).getIconFromPass(stack, i + 1);
               colors[i] = ((IExtraPasses) stack.getItem()).getColorFromPass(stack, i + 1);
               shiny[i] = ((IExtraPasses) stack.getItem()).getShinyFromPass(stack, i + 1);
          }
          
          final TextureManager engine = Minecraft.getMinecraft().getTextureManager();
          
          if (type == ItemRenderType.INVENTORY)
          {
               RenderAssets.renderItemInventory(stack, engine, passes, icons, colors, shiny, zLevel);
          }
          
          if (type == ItemRenderType.EQUIPPED)
          {
               GL11.glTranslatef(0.2F, -0.3F, -0.27F);
               GL11.glRotatef(10, 1, 0, 0);
          }
          
          if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
          {
               RenderAssets.renderEquippedItem(stack, engine, passes, icons, colors, shiny);
          }
          
          if (type == ItemRenderType.ENTITY)
          {
               final EntityItem entityItem = (EntityItem) data[1];
               
               RenderAssets.renderEntityItem(entityItem, stack, passes, icons, colors, shiny);
          }
          
          GL11.glPopMatrix();
     }
}
