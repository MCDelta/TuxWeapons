package mcdelta.tuxweapons.client.entity;

import mcdelta.core.assets.client.RenderAssets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.tuxweapons.entity.EntitySpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class RenderSpear extends Render
{
     private static final ResourceLocation glint = new ResourceLocation("textures/misc/enchanted_item_glint.png");
     
     
     
     
     public void renderSpear (EntitySpear spear, double par2, double par4, double par6, float par8, float par9)
     {
          ItemStack stack = spear.stack;
          
          if (stack != null)
          {
               GL11.glPushMatrix();
               
               int passes = ((IExtraPasses) stack.getItem()).getPasses(stack);
               Icon[] icons = new Icon[passes];
               int[] colors = new int[passes];
               boolean[] shiny = new boolean[passes];
               
               for (int i = 0; i < passes; i++)
               {
                    icons[i] = ((IExtraPasses) stack.getItem()).getIconFromPass(stack, i + 1);
               }
               
               for (int i = 0; i < passes; i++)
               {
                    colors[i] = ((IExtraPasses) stack.getItem()).getColorFromPass(stack, i + 1);
               }
               
               for (int i = 0; i < passes; i++)
               {
                    shiny[i] = ((IExtraPasses) stack.getItem()).getShinyFromPass(stack, i + 1);
               }
               
               TextureManager engine = Minecraft.getMinecraft().getTextureManager();
               
               GL11.glTranslatef((float) par2, (float) par4, (float) par6);
               
               GL11.glRotatef(spear.prevRotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(spear.prevRotationPitch - 45, 0.0F, 0.0F, 1.0F);
               
               float f11 = (float)spear.arrowShake - par9;

               if (f11 > 0.0F)
               {
                   float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
                   GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
               }
               
               float scale = 1.5F;
               GL11.glScalef(scale, scale, scale);
               
               RenderAssets.renderItemInWorld(stack, engine, passes, icons, colors, shiny);
               
               GL11.glPopMatrix();
          }
     }
     
     
     
     
     @Override
     public void doRender (Entity entity, double par2, double par4, double par6, float par8, float par9)
     {
          this.renderSpear((EntitySpear) entity, par2, par4, par6, par8, par9);
     }
     
     
     
     
     @Override
     protected ResourceLocation getEntityTexture (Entity entity)
     {
          EntitySpear spear = (EntitySpear) entity;
          return this.renderManager.renderEngine.getResourceLocation(spear.stack.getItemSpriteNumber());
     }
}
