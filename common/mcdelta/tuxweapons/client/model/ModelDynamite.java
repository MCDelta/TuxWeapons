package mcdelta.tuxweapons.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDynamite extends ModelBase
{

    public ModelRenderer dynamite;

    public ModelDynamite()
    {
        this(0, 20, 48, 32);
    }

    public ModelDynamite(int par1, int par2, int par3, int par4)
    {
        this.textureWidth = par3;
        this.textureHeight = par4;
        this.dynamite = new ModelRenderer(this, par1, par2);
        this.dynamite.addBox(-4.0F, -8.0F, -4.0F, 4, 4, 8, 0.0F);
        this.dynamite.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.dynamite.render(par7);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.dynamite.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.dynamite.rotateAngleX = par5 / (180F / (float) Math.PI);
    }
}