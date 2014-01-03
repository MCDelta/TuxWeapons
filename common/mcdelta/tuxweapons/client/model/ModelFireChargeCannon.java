package mcdelta.tuxweapons.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFireChargeCannon extends ModelBase
{
     public ModelRenderer Shape1;
     public ModelRenderer Shape2;
     public ModelRenderer Shape3;
     
     
     
     
     public ModelFireChargeCannon ()
     {
          this.textureWidth = 64;
          this.textureHeight = 64;
          
          this.Shape1 = new ModelRenderer(this, 0, 0);
          this.Shape1.addBox(-5F, -5F, -5F, 10, 10, 8);
          this.Shape1.setRotationPoint(0F, 0F, 0F);
          this.Shape1.setTextureSize(64, 64);
          this.Shape1.mirror = true;
          this.setRotation(this.Shape1, 0F, 0F, 0F);
          this.Shape2 = new ModelRenderer(this, 0, 18);
          this.Shape2.addBox(-5F, -5F, -2F, 10, 10, 4);
          this.Shape2.setRotationPoint(0F, 0F, -11F);
          this.Shape2.setTextureSize(64, 64);
          this.Shape2.mirror = true;
          this.setRotation(this.Shape2, 0F, 0F, 0F);
          this.Shape3 = new ModelRenderer(this, 0, 32);
          this.Shape3.addBox(-4F, -4F, 0F, 8, 8, 4);
          this.Shape3.setRotationPoint(0F, 0F, -9F);
          this.Shape3.setTextureSize(64, 64);
          this.Shape3.mirror = true;
          this.setRotation(this.Shape3, 0F, 0F, 0F);
     }
     
     
     
     
     @Override
     public void render (final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
     {
          super.render(entity, f, f1, f2, f3, f4, f5);
          this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
          this.Shape1.render(f5);
          this.Shape2.render(f5);
          this.Shape3.render(f5);
     }
     
     
     
     
     private void setRotation (final ModelRenderer model, final float x, final float y, final float z)
     {
          model.rotateAngleX = x;
          model.rotateAngleY = y;
          model.rotateAngleZ = z;
     }
     
     
     
     
     @Override
     public void setRotationAngles (final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity par1Entity)
     {
          super.setRotationAngles(f, f1, f2, f3, f4, f5, par1Entity);
     }
}
