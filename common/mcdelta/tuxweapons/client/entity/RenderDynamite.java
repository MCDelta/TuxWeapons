package mcdelta.tuxweapons.client.entity;

import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.client.model.ModelDynamite;
import mcdelta.tuxweapons.entity.EntityDynamite;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class RenderDynamite extends Render
{
     
     ModelDynamite                  model    = new ModelDynamite();
     private final ResourceLocation location = new ResourceLocation(TuxWeapons.MOD_ID.toLowerCase(), "textures/models/weapons.png");
     
     
     
     
     private float func_82400_a (final float par1, final float x, final float par3)
     {
          float f3;
          
          for (f3 = x - par1; f3 < -180.0F; f3 += 360.0F)
          {
               ;
          }
          
          while (f3 >= 180.0F)
          {
               f3 -= 360.0F;
          }
          
          return par1 + par3 * f3;
     }
     
     
     
     
     public void renderDynamite (final EntityDynamite dynamite, final double x, final double y, final double z, final float par8, final float par9)
     {
          GL11.glPushMatrix();
          GL11.glDisable(GL11.GL_CULL_FACE);
          
          renderManager.renderEngine.bindTexture(location);
          
          final float f2 = func_82400_a(dynamite.prevRotationYaw, dynamite.rotationYaw, par9);
          final float f3 = dynamite.prevRotationPitch + (dynamite.rotationPitch - dynamite.prevRotationPitch) * par9;
          GL11.glTranslatef((float) x, (float) y, (float) z);
          final float f4 = 0.0625F;
          GL11.glEnable(GL12.GL_RESCALE_NORMAL);
          GL11.glScalef(-1.0F, -1.0F, 1.0F);
          GL11.glEnable(GL11.GL_ALPHA_TEST);
          
          model.render(dynamite, 0.0F, 0.0F, 0.0F, f2, f3, f4);
          GL11.glPopMatrix();
     }
     
     
     
     
     @Override
     public void doRender (final Entity par1Entity, final double x, final double y, final double z, final float par8, final float par9)
     {
          renderDynamite((EntityDynamite) par1Entity, x, y, z, par8, par9);
     }
     
     
     
     
     @Override
     protected ResourceLocation getEntityTexture (final Entity entity)
     {
          return location;
     }
}
