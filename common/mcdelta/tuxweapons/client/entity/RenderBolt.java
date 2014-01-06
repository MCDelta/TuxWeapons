package mcdelta.tuxweapons.client.entity;

import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.entity.EntityBolt;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class RenderBolt extends Render
{
     private final ResourceLocation location = new ResourceLocation(TuxWeapons.MOD_ID.toLowerCase(), "textures/models/weapons.png");
     
     
     
     
     public void renderBolt (final EntityBolt bolt, final double x, final double y, final double z, final float par8, final float par9)
     {
          renderManager.renderEngine.bindTexture(location);
          
          GL11.glPushMatrix();
          GL11.glTranslatef((float) x, (float) y, (float) z);
          GL11.glRotatef(bolt.prevRotationYaw + (bolt.rotationYaw - bolt.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
          GL11.glRotatef(bolt.prevRotationPitch + (bolt.rotationPitch - bolt.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
          final Tessellator tessellator = Tessellator.instance;
          final byte b0 = 0;
          final float f2 = 0.0F;
          final float f3 = 0.5F;
          final float f4 = (3 + b0 * 10) / 32.0F;
          final float f5 = (7 + b0 * 10) / 32.0F;
          final float f10 = 0.1F;
          GL11.glEnable(GL12.GL_RESCALE_NORMAL);
          final float f11 = bolt.boltShake - par9;
          
          if (f11 > 0.0F)
          {
               final float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
               GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
          }
          
          GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
          GL11.glScalef(f10, f10, f10);
          GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
          GL11.glNormal3f(f10, 0.0F, 0.0F);
          GL11.glNormal3f(-f10, 0.0F, 0.0F);
          
          for (int i = 0; i < 4; ++i)
          {
               GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
               GL11.glNormal3f(0.0F, 0.0F, f10);
               tessellator.startDrawingQuads();
               tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, f2, f4);
               tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, f3, f4);
               tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, f3, f5);
               tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, f2, f5);
               tessellator.draw();
          }
          
          GL11.glDisable(GL12.GL_RESCALE_NORMAL);
          GL11.glPopMatrix();
     }
     
     
     
     
     @Override
     public void doRender (final Entity par1Entity, final double x, final double y, final double z, final float par8, final float par9)
     {
          renderBolt((EntityBolt) par1Entity, x, y, z, par8, par9);
     }
     
     
     
     
     @Override
     protected ResourceLocation getEntityTexture (final Entity entity)
     {
          return location;
     }
}
