package mcdelta.tuxweapons.client.model;

import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelFireChargeCannon extends ModelBase
{
    private ResourceLocation location = new ResourceLocation(TuxWeapons.MOD_ID.toLowerCase(), "textures/models/weapons.png");

    public ModelRenderer     Shape1;
    public ModelRenderer     Shape2;
    public ModelRenderer     Shape3;

    public ModelFireChargeCannon()
    {
        textureWidth = 64;
        textureHeight = 64;

        Shape1 = new ModelRenderer(this, 0, 0);
        Shape1.addBox(-5F, -5F, -5F, 10, 10, 8);
        Shape1.setRotationPoint(0F, 0F, 0F);
        Shape1.setTextureSize(64, 64);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);
        Shape2 = new ModelRenderer(this, 0, 18);
        Shape2.addBox(-5F, -5F, -2F, 10, 10, 4);
        Shape2.setRotationPoint(0F, 0F, -11F);
        Shape2.setTextureSize(64, 64);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);
        Shape3 = new ModelRenderer(this, 0, 32);
        Shape3.addBox(-4F, -4F, 0F, 8, 8, 4);
        Shape3.setRotationPoint(0F, 0F, -9F);
        Shape3.setTextureSize(64, 64);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Shape1.render(f5);
        Shape2.render(f5);
        Shape3.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par1Entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, par1Entity);
    }
}