package mcdelta.tuxweapons.client.entity;

import mcdelta.tuxweapons.TuxWeaponsCore;
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

@SideOnly(Side.CLIENT)
public class RenderBolt extends Render
{
    private ResourceLocation location = new ResourceLocation(TuxWeaponsCore.MOD_ID.toLowerCase(), "textures/models/weapons.png");

    public void renderBolt(EntityBolt bolt, double x, double y, double z, float par8, float par9)
    {
        this.renderManager.renderEngine.bindTexture(location);

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(bolt.prevRotationYaw + (bolt.rotationYaw - bolt.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(bolt.prevRotationPitch + (bolt.rotationPitch - bolt.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        byte b0 = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (3 + b0 * 10) / 32.0F;
        float f5 = (7 + b0 * 10) / 32.0F;
        float f10 = 0.1F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f11 = bolt.boltShake - par9;

        if (f11 > 0.0F)
        {
            float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
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
    public void doRender(Entity par1Entity, double x, double y, double z, float par8, float par9)
    {
        this.renderBolt((EntityBolt) par1Entity, x, y, z, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return location;
    }
}