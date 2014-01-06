package mcdelta.tuxweapons.client.item;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.client.RenderAssets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderItemPotion implements IItemRenderer
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
          
          final Item item = stack.getItem();
          
          final int passes = item.getRenderPasses(stack.getItemDamage());
          final Icon[] icons = new Icon[passes];
          final int[] colors = new int[passes];
          final boolean[] shiny = new boolean[passes];
          
          for (int i = 0; i < passes; i++)
          {
               icons[i] = item.getIcon(stack, i);
               colors[i] = item.getColorFromItemStack(stack, i);
               shiny[i] = false;
          }
          
          if (item instanceof ItemPotion)
          {
               final List<PotionEffect> effects = Item.potion.getEffects(stack);
               
               if (effects != null)
               {
                    final float[] r = new float[effects.size()];
                    final float[] g = new float[effects.size()];
                    final float[] b = new float[effects.size()];
                    
                    for (int i = 0; i < effects.size(); i++)
                    {
                         final PotionEffect effect = effects.get(i);
                         final float[] rgb = Assets.hexToRGB(Potion.potionTypes[effect.getPotionID()].getLiquidColor());
                         
                         r[i] = rgb[0];
                         g[i] = rgb[1];
                         b[i] = rgb[2];
                    }
                    
                    final float finalR = Assets.average(r);
                    final float finalG = Assets.average(g);
                    final float finalB = Assets.average(b);
                    
                    final Color color = new Color(finalR, finalG, finalB);
                    
                    colors[0] = color.getRGB();
               }
          }
          
          final TextureManager engine = Minecraft.getMinecraft().getTextureManager();
          
          if (type == ItemRenderType.INVENTORY)
          {
               RenderAssets.renderItemInventory(stack, engine, passes, icons, colors, shiny, zLevel);
          }
          
          if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
          {
               RenderAssets.renderEquippedItem(stack, engine, passes, icons, colors, shiny);
          }
          
          if (type == ItemRenderType.ENTITY)
          {
               final EntityItem entityItem = (EntityItem) data[1];
               
               GL11.glScalef(2F, 2F, 2F);
               RenderAssets.renderEntityItem(entityItem, stack, passes, icons, colors, shiny);
          }
          
          GL11.glPopMatrix();
     }
}
