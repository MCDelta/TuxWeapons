package mcdelta.tuxweapons.specials.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PotionCure extends PotionTW
{
    public PotionCure(String s, int color, int x, int y)
    {
        super(s, color, x, y);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase living, BaseAttributeMap map, int i)
    {
        super.removeAttributesModifiersFromEntity(living, map, i);

        performEffect(living, i);
    }

    @Override
    public void performEffect(EntityLivingBase living, int i)
    {
        living.curePotionEffects(new ItemStack(Item.bucketMilk));
    }

    @Override
    public void affectEntity(EntityLivingBase entity, EntityLivingBase target, int i, double ii)
    {
        performEffect(target, i);
    }

    @Override
    public boolean isInstant()
    {
        return true;
    }
}
