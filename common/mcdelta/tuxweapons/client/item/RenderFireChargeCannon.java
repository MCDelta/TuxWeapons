package mcdelta.tuxweapons.client.item;

import mcdelta.tuxweapons.TuxWeaponsCore;
import mcdelta.tuxweapons.client.model.ModelFireChargeCannon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderFireChargeCannon implements IItemRenderer
{
    ModelFireChargeCannon    cannonModel;
    private ResourceLocation location = new ResourceLocation(TuxWeaponsCore.MOD_ID.toLowerCase(), "textures/models/FireChargeCannon.png");
    private Minecraft        mc;

    public RenderFireChargeCannon()
    {
        this.mc = Minecraft.getMinecraft();
        cannonModel = new ModelFireChargeCannon();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
            {
                case EQUIPPED:
                    return true;
                case EQUIPPED_FIRST_PERSON:
                    return true;
                default:
                    return false;
            }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data)
    {
        EntityPlayer player = (EntityPlayer) data[1];

        GL11.glPushMatrix();

        TextureManager engine = Minecraft.getMinecraft().getTextureManager();
        Tessellator tessellator = Tessellator.instance;
        engine.bindTexture(location);

        if (type == ItemRenderType.EQUIPPED)
        {
            float scale = 1.8F;
            GL11.glScalef(scale, scale, scale);

            GL11.glRotatef(190.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-15.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0, -0.3F, 0.5F);
        }

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            float scale = 3F;
            GL11.glScalef(scale, scale, scale);

            GL11.glTranslatef(0.2F, 0.2F, 0.5F);

            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);

            if (player.getItemInUseCount() == 0)
            {
                GL11.glRotatef(20, 0.0F, 0.0F, 0.0F);
            }
        }

        cannonModel.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }
}