package mcdelta.tuxweapons.client.item;

import mcdelta.core.assets.client.RenderAssets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderCrossbow implements IItemRenderer
{

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
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        TextureManager engine = Minecraft.getMinecraft().getTextureManager();

        if ((type == ItemRenderType.EQUIPPED) || (type == ItemRenderType.EQUIPPED_FIRST_PERSON))
        {
            GL11.glPushMatrix();

            EntityLivingBase player = (EntityLivingBase) data[1];

            if (type == ItemRenderType.EQUIPPED)
            {
                GL11.glTranslatef(0.0F, -0.1F, 0.0F);
            }

            else
            {
                if (((EntityPlayer) player).getItemInUseCount() > 0)
                {
                    GL11.glRotatef(-10.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
                }
            }

            GL11.glRotatef(35.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(40.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);

            RenderAssets.renderEquippedItem(item, engine, player.getItemIcon(item, 0));

            GL11.glPopMatrix();
        }
    }
}
