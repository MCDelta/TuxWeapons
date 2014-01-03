package mcdelta.tuxweapons.client.entity;

import mcdelta.core.assets.client.RenderAssets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.tuxweapons.entity.EntityGrappHook;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class RenderGrappHook extends Render
{
     public void renderGrappHook (final EntityGrappHook grappHook, double x, double y, double z, final float par8, final float par9)
     {
          final ItemStack stack = grappHook.stack;
          
          if (stack != null)
          {
               GL11.glPushMatrix();
               
               final int passes = ((IExtraPasses) stack.getItem()).getPasses(stack);
               final Icon[] icons = new Icon[passes];
               final int[] colors = new int[passes];
               final boolean[] shiny = new boolean[passes];
               
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
               
               GL11.glTranslatef((float) x, (float) y + 0.04F, (float) z);
               
               GL11.glRotatef(grappHook.prevRotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(grappHook.prevRotationPitch - 135, 0.0F, 0.0F, 1.0F);
               
               final float f11 = grappHook.arrowShake - par9;
               
               if (f11 > 0.0F)
               {
                    final float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
                    GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
               }
               
               final float scale = 1.5F;
               GL11.glScalef(scale, scale, scale);
               
               RenderAssets.renderItemInWorld(stack, passes, icons, colors, shiny);
               
               GL11.glPopMatrix();
               
               GL11.glPushMatrix();
               
               final Entity hook = grappHook.owner;
               final Entity owner = grappHook;
               
               if (hook != null)
               {
                    y -= (3.5 - owner.height) * 0.5D;
                    final Tessellator tessellator = Tessellator.instance;
                    final double d3 = this.leashFunction(hook.prevRotationYaw, hook.rotationYaw, par9 * 0.5F) * 0.01745329238474369D;
                    final double d4 = this.leashFunction(hook.prevRotationPitch, hook.rotationPitch, par9 * 0.5F) * 0.01745329238474369D;
                    double d5 = Math.cos(d3);
                    double d6 = Math.sin(d3);
                    final double d7 = Math.sin(d4);
                    
                    final double d8 = Math.cos(d4);
                    final double d9 = this.leashFunction(hook.prevPosX, hook.posX, par9) - d5 * 0.7D - d6 * 0.5D * d8;
                    final double d10 = this.leashFunction(hook.prevPosY + (hook.getEyeHeight() + 1.7D) * 0.7D, hook.posY + (hook.getEyeHeight() + 1.7D) * 0.7D, par9) - d7 * 0.5D - 0.25D;
                    final double d11 = this.leashFunction(hook.prevPosZ, hook.posZ, par9) - d6 * 0.7D + d5 * 0.5D * d8;
                    final double d12 = this.leashFunction(owner.prevRotationYaw, owner.rotationYaw, par9) * 0.01745329238474369D + Math.PI / 2D;
                    d5 = Math.cos(d12) * owner.width * 0.4D;
                    d6 = Math.sin(d12) * owner.width * 0.4D;
                    final double d13 = this.leashFunction(owner.prevPosX, owner.posX, par9) + d5;
                    final double d14 = this.leashFunction(owner.prevPosY, owner.posY, par9);
                    final double d15 = this.leashFunction(owner.prevPosZ, owner.posZ, par9) + d6;
                    x += d5;
                    z += d6;
                    final double d16 = (float) (d9 - d13);
                    final double d17 = (float) (d10 - d14);
                    final double d18 = (float) (d11 - d15);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_CULL_FACE);
                    tessellator.startDrawing(5);
                    int i;
                    float f2;
                    
                    for (i = 0; i <= 24; ++i)
                    {
                         if (i % 2 == 0)
                         {
                              tessellator.setColorRGBA_F(0.52F, 0.52F, 0.56F, 1.0F);
                         }
                         else
                         {
                              tessellator.setColorRGBA_F(0.36F, 0.36F, 0.37F, 1.0F);
                         }
                         
                         f2 = i / 24.0F;
                         tessellator.addVertex(x + d16 * f2 + 0.0D, y + d17 * (f2 * f2 + f2) * 0.5D + ((24.0F - i) / 18.0F + 0.125F), z + d18 * f2);
                         tessellator.addVertex(x + d16 * f2 + 0.025D, y + d17 * (f2 * f2 + f2) * 0.5D + ((24.0F - i) / 18.0F + 0.125F) + 0.025D, z + d18 * f2);
                    }
                    
                    tessellator.draw();
                    tessellator.startDrawing(5);
                    
                    for (i = 0; i <= 24; ++i)
                    {
                         if (i % 2 == 0)
                         {
                              tessellator.setColorRGBA_F(0.52F, 0.52F, 0.56F, 1.0F);
                         }
                         else
                         {
                              tessellator.setColorRGBA_F(0.36F, 0.36F, 0.37F, 1.0F);
                         }
                         
                         f2 = i / 24.0F;
                         tessellator.addVertex(x + d16 * f2 + 0.0D, y + d17 * (f2 * f2 + f2) * 0.5D + ((24.0F - i) / 18.0F + 0.125F) + 0.025D, z + d18 * f2);
                         tessellator.addVertex(x + d16 * f2 + 0.025D, y + d17 * (f2 * f2 + f2) * 0.5D + ((24.0F - i) / 18.0F + 0.125F), z + d18 * f2 + 0.025D);
                    }
                    
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_CULL_FACE);
               }
               
               GL11.glPopMatrix();
          }
     }
     
     
     
     
     private double leashFunction (final double x, final double y, final double z)
     {
          return x + (y - x) * z;
     }
     
     
     
     
     @Override
     public void doRender (final Entity entity, final double par2, final double par4, final double par6, final float par8, final float par9)
     {
          this.renderGrappHook((EntityGrappHook) entity, par2, par4, par6, par8, par9);
     }
     
     
     
     
     @Override
     protected ResourceLocation getEntityTexture (final Entity entity)
     {
          final EntityGrappHook grappHook = (EntityGrappHook) entity;
          return this.renderManager.renderEngine.getResourceLocation(grappHook.stack.getItemSpriteNumber());
     }
}
