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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderItemPotion implements IItemRenderer
{
     
     private static final ResourceLocation glint  = new ResourceLocation("textures/misc/enchanted_item_glint.png");
     Random                                rand   = new Random();
     int                                   zLevel = 0;
     
     
     
     
     @Override
     public boolean handleRenderType (ItemStack item, ItemRenderType type)
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
     public boolean shouldUseRenderHelper (ItemRenderType type, ItemStack item, ItemRendererHelper helper)
     {
          return (helper == ItemRendererHelper.ENTITY_ROTATION && Minecraft.getMinecraft().gameSettings.fancyGraphics) || helper == ItemRendererHelper.ENTITY_BOBBING;
     }
     
     
     
     
     @Override
     public void renderItem (ItemRenderType type, ItemStack stack, Object... data)
     {
          GL11.glPushMatrix();
          
          Item item = stack.getItem();
          
          int passes = item.getRenderPasses(stack.getItemDamage());
          Icon[] icons = new Icon[passes];
          int[] colors = new int[passes];
          boolean[] shiny = new boolean[passes];
          
          for (int i = 0; i < passes; i++)
          {
               icons[i] = item.getIcon(stack, i);
               colors[i] = item.getColorFromItemStack(stack, i);
               shiny[i] = false;
          }
          
          if (item instanceof ItemPotion)
          {
               List<PotionEffect> effects = Item.potion.getEffects(stack);
               
               if (effects != null)
               {
                    float[] r = new float[effects.size()];
                    float[] g = new float[effects.size()];
                    float[] b = new float[effects.size()];
                    
                    for (int i = 0; i < effects.size(); i++)
                    {
                         PotionEffect effect = effects.get(i);
                         float[] rgb = Assets.hexToRGB(Potion.potionTypes[effect.getPotionID()].getLiquidColor());
                         
                         r[i] = rgb[0];
                         g[i] = rgb[1];
                         b[i] = rgb[2];
                    }
                    
                    float finalR = Assets.average(r);
                    float finalG = Assets.average(g);
                    float finalB = Assets.average(b);
                    
                    Color color = new Color(finalR, finalG, finalB);
                    
                    colors[0] = color.getRGB();
               }
          }
          
          TextureManager engine = Minecraft.getMinecraft().getTextureManager();
          
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
               EntityItem entityItem = (EntityItem) data[1];
               
               GL11.glScalef(2F, 2F, 2F);
               RenderAssets.renderEntityItem(entityItem, stack, passes, icons, colors, shiny);
          }
          
          GL11.glPopMatrix();
     }
}
