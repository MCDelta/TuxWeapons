package mcdelta.tuxweapons.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class SkyRenderer extends IRenderHandler
{
     private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
     private static final ResourceLocation locationSunPng        = new ResourceLocation("textures/environment/sun.png");
     
     
     
     
     @Override
     public void render (float partialTicks, WorldClient world, Minecraft mc)
     {
          RenderGlobal renderer = mc.renderGlobal;
          GL11.glDisable(GL11.GL_TEXTURE_2D);
          Vec3 vec3 = renderer.theWorld.getSkyColor(renderer.mc.renderViewEntity, partialTicks);
          float f1 = (float) vec3.xCoord;
          float f2 = (float) vec3.yCoord;
          float f3 = (float) vec3.zCoord;
          float f4;
          
          if (renderer.mc.gameSettings.anaglyph)
          {
               float f5 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
               float f6 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
               f4 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
               f1 = f5;
               f2 = f6;
               f3 = f4;
          }
          
          GL11.glColor3f(f1, f2, f3);
          Tessellator tessellator1 = Tessellator.instance;
          GL11.glDepthMask(false);
          GL11.glEnable(GL11.GL_FOG);
          GL11.glColor3f(f1, f2, f3);
          GL11.glCallList((Integer) ReflectionHelper.getPrivateValue(RenderGlobal.class, renderer, "glSkyList"));
          GL11.glDisable(GL11.GL_FOG);
          GL11.glDisable(GL11.GL_ALPHA_TEST);
          GL11.glEnable(GL11.GL_BLEND);
          GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
          RenderHelper.disableStandardItemLighting();
          float[] afloat = renderer.theWorld.provider.calcSunriseSunsetColors(renderer.theWorld.getCelestialAngle(partialTicks), partialTicks);
          float f7;
          float f8;
          float f9;
          float f10;
          
          if (afloat != null)
          {
               GL11.glDisable(GL11.GL_TEXTURE_2D);
               GL11.glShadeModel(GL11.GL_SMOOTH);
               GL11.glPushMatrix();
               GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(MathHelper.sin(renderer.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
               f4 = afloat[0];
               f7 = afloat[1];
               f8 = afloat[2];
               float f11;
               
               if (renderer.mc.gameSettings.anaglyph)
               {
                    f9 = (f4 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
                    f10 = (f4 * 30.0F + f7 * 70.0F) / 100.0F;
                    f11 = (f4 * 30.0F + f8 * 70.0F) / 100.0F;
                    f4 = f9;
                    f7 = f10;
                    f8 = f11;
               }
               
               tessellator1.startDrawing(6);
               tessellator1.setColorRGBA_F(f4, f7, f8, afloat[3]);
               tessellator1.addVertex(0.0D, 100.0D, 0.0D);
               byte b0 = 16;
               tessellator1.setColorRGBA_F(afloat[0], afloat[1], afloat[2], 0.0F);
               
               for (int j = 0; j <= b0; ++j)
               {
                    f11 = (float) j * (float) Math.PI * 2.0F / (float) b0;
                    float f12 = MathHelper.sin(f11);
                    float f13 = MathHelper.cos(f11);
                    tessellator1.addVertex((double) (f12 * 120.0F), (double) (f13 * 120.0F), (double) (-f13 * 40.0F * afloat[3]));
               }
               
               tessellator1.draw();
               GL11.glPopMatrix();
               GL11.glShadeModel(GL11.GL_FLAT);
          }
          
          GL11.glEnable(GL11.GL_TEXTURE_2D);
          GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
          GL11.glPushMatrix();
          f4 = 1.0F - renderer.theWorld.getRainStrength(partialTicks);
          f7 = 0.0F;
          f8 = 0.0F;
          f9 = 0.0F;
          GL11.glColor4f(1.0F, 1.0F, 1.0F, f4);
          GL11.glTranslatef(f7, f8, f9);
          GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
          GL11.glRotatef(renderer.theWorld.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
          f10 = 30.0F;
          renderer.renderEngine.bindTexture(locationSunPng);
          tessellator1.startDrawingQuads();
          tessellator1.addVertexWithUV((double) (-f10), 100.0D, (double) (-f10), 0.0D, 0.0D);
          tessellator1.addVertexWithUV((double) f10, 100.0D, (double) (-f10), 1.0D, 0.0D);
          tessellator1.addVertexWithUV((double) f10, 100.0D, (double) f10, 1.0D, 1.0D);
          tessellator1.addVertexWithUV((double) (-f10), 100.0D, (double) f10, 0.0D, 1.0D);
          tessellator1.draw();
          
          f10 = 20.0F;
          renderer.renderEngine.bindTexture(locationMoonPhasesPng);
          int k = renderer.theWorld.getMoonPhase();
          int l = k % 4;
          int i1 = k / 4 % 2;
          float f14 = (float) (l + 0) / 4.0F;
          float f15 = (float) (i1 + 0) / 2.0F;
          float f16 = (float) (l + 1) / 4.0F;
          float f17 = (float) (i1 + 1) / 2.0F;
          tessellator1.startDrawingQuads();
          tessellator1.addVertexWithUV((double) (-f10), -100.0D, (double) f10, (double) f16, (double) f17);
          tessellator1.addVertexWithUV((double) f10, -100.0D, (double) f10, (double) f14, (double) f17);
          tessellator1.addVertexWithUV((double) f10, -100.0D, (double) (-f10), (double) f14, (double) f15);
          tessellator1.addVertexWithUV((double) (-f10), -100.0D, (double) (-f10), (double) f16, (double) f15);
          tessellator1.draw();
          
          GL11.glDisable(GL11.GL_TEXTURE_2D);
          float f18 = renderer.theWorld.getStarBrightness(partialTicks) * f4;
          
          if (f18 > 0.0F)
          {
               GL11.glColor4f(f18, f18, f18, f18);
               GL11.glCallList((Integer) ReflectionHelper.getPrivateValue(RenderGlobal.class, renderer, "starGLCallList"));
          }
          
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          GL11.glPopMatrix();
          
          //==========
          //Start MCDelta code
          //==========
          
          double posX = mc.thePlayer.posX;
          double posZ = mc.thePlayer.posZ;
          int x = 1101;
          int z = 350;
          
          double d4 = (double) x - posX;
          double d5 = (double) z - posZ;
          d4 /= 2;
          d5 /= 2;
          
          if (d4 > 90)
          {
               d4 = 90;
          }
          
          if (d4 < -90)
          {
               d4 = -90;
          }
          
          if (d5 > 90)
          {
               d5 = 90;
          }
          
          if (d5 < -90)
          {
               d5 = -90;
          }
          
          GL11.glEnable(GL11.GL_TEXTURE_2D);
          GL11.glPushMatrix();
          GL11.glScalef(0.6F, 0.6F, 0.6F);
          GL11.glRotated(-d4, 0.0F, 0.0F, 1.0F);
          GL11.glRotated(d5, 1.0F, 0.0F, 0.0F);
          GL11.glRotatef(0, 1.0F, 0.0F, 0.0F);
          f10 = 30.0F;
          renderer.renderEngine.bindTexture(locationSunPng);
          tessellator1.startDrawingQuads();
          tessellator1.addVertexWithUV((double) (-f10), 100.0D, (double) (-f10), 0.0D, 0.0D);
          tessellator1.addVertexWithUV((double) f10, 100.0D, (double) (-f10), 1.0D, 0.0D);
          tessellator1.addVertexWithUV((double) f10, 100.0D, (double) f10, 1.0D, 1.0D);
          tessellator1.addVertexWithUV((double) (-f10), 100.0D, (double) f10, 0.0D, 1.0D);
          tessellator1.draw();
          GL11.glDisable(GL11.GL_TEXTURE_2D);
          GL11.glDisable(GL11.GL_BLEND);
          GL11.glEnable(GL11.GL_ALPHA_TEST);
          GL11.glEnable(GL11.GL_FOG);
          GL11.glPopMatrix();
          
          //========== 
          //End MCDelta code
          //==========
          
          GL11.glDisable(GL11.GL_TEXTURE_2D);
          GL11.glColor3f(0.0F, 0.0F, 0.0F);
          double d0 = renderer.mc.thePlayer.getPosition(partialTicks).yCoord - renderer.theWorld.getHorizon();
          
          if (d0 < 0.0D)
          {
               GL11.glPushMatrix();
               GL11.glTranslatef(0.0F, 12.0F, 0.0F);
               GL11.glCallList((Integer) ReflectionHelper.getPrivateValue(RenderGlobal.class, renderer, "glSkyList2"));
               GL11.glPopMatrix();
               f8 = 1.0F;
               f9 = -((float) (d0 + 65.0D));
               f10 = -f8;
               tessellator1.startDrawingQuads();
               tessellator1.setColorRGBA_I(0, 255);
               tessellator1.addVertex((double) (-f8), (double) f9, (double) f8);
               tessellator1.addVertex((double) f8, (double) f9, (double) f8);
               tessellator1.addVertex((double) f8, (double) f10, (double) f8);
               tessellator1.addVertex((double) (-f8), (double) f10, (double) f8);
               tessellator1.addVertex((double) (-f8), (double) f10, (double) (-f8));
               tessellator1.addVertex((double) f8, (double) f10, (double) (-f8));
               tessellator1.addVertex((double) f8, (double) f9, (double) (-f8));
               tessellator1.addVertex((double) (-f8), (double) f9, (double) (-f8));
               tessellator1.addVertex((double) f8, (double) f10, (double) (-f8));
               tessellator1.addVertex((double) f8, (double) f10, (double) f8);
               tessellator1.addVertex((double) f8, (double) f9, (double) f8);
               tessellator1.addVertex((double) f8, (double) f9, (double) (-f8));
               tessellator1.addVertex((double) (-f8), (double) f9, (double) (-f8));
               tessellator1.addVertex((double) (-f8), (double) f9, (double) f8);
               tessellator1.addVertex((double) (-f8), (double) f10, (double) f8);
               tessellator1.addVertex((double) (-f8), (double) f10, (double) (-f8));
               tessellator1.addVertex((double) (-f8), (double) f10, (double) (-f8));
               tessellator1.addVertex((double) (-f8), (double) f10, (double) f8);
               tessellator1.addVertex((double) f8, (double) f10, (double) f8);
               tessellator1.addVertex((double) f8, (double) f10, (double) (-f8));
               tessellator1.draw();
          }
          
          if (renderer.theWorld.provider.isSkyColored())
          {
               GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
          }
          else
          {
               GL11.glColor3f(f1, f2, f3);
          }
          
          GL11.glPushMatrix();
          GL11.glTranslatef(0.0F, -((float) (d0 - 16.0D)), 0.0F);
          GL11.glCallList((Integer) ReflectionHelper.getPrivateValue(RenderGlobal.class, renderer, "glSkyList2"));
          GL11.glPopMatrix();
          GL11.glEnable(GL11.GL_TEXTURE_2D);
          GL11.glDepthMask(true);
     }
     
}