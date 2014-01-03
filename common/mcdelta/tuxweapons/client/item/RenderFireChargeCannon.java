package mcdelta.tuxweapons.client.item;

import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.client.model.ModelFireChargeCannon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderFireChargeCannon implements IItemRenderer
{
     ModelFireChargeCannon          cannonModel;
     private final ResourceLocation location = new ResourceLocation(TuxWeapons.MOD_ID.toLowerCase(), "textures/models/FireChargeCannon.png");
     
     
     
     
     public RenderFireChargeCannon ()
     {
          Minecraft.getMinecraft();
          this.cannonModel = new ModelFireChargeCannon();
     }
     
     
     
     
     @Override
     public boolean handleRenderType (final ItemStack item, final ItemRenderType type)
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
     public boolean shouldUseRenderHelper (final ItemRenderType type, final ItemStack item, final ItemRendererHelper helper)
     {
          return false;
     }
     
     
     
     
     @Override
     public void renderItem (final ItemRenderType type, final ItemStack stack, final Object... data)
     {
          final EntityPlayer player = (EntityPlayer) data[1];
          
          GL11.glPushMatrix();
          
          final TextureManager engine = Minecraft.getMinecraft().getTextureManager();
          engine.bindTexture(this.location);
          
          if (type == ItemRenderType.EQUIPPED)
          {
               final float scale = 1.8F;
               GL11.glScalef(scale, scale, scale);
               
               GL11.glRotatef(190.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(-15.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(0, -0.3F, 0.5F);
          }
          
          if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
          {
               final float scale = 3F;
               GL11.glScalef(scale, scale, scale);
               
               GL11.glTranslatef(0.2F, 0.2F, 0.5F);
               
               GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
               
               if (player.getItemInUseCount() == 0)
               {
                    GL11.glRotatef(20, 0.0F, 0.0F, 0.0F);
               }
          }
          
          this.cannonModel.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
          
          GL11.glPopMatrix();
     }
}
