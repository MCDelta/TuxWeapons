package mcdelta.tuxweapons.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PotionCure extends PotionTW
{
     public PotionCure (final String s, final int color, final int x, final int y)
     {
          super(s, color, x, y);
     }
     
     
     
     
     @Override
     public void removeAttributesModifiersFromEntity (final EntityLivingBase living, final BaseAttributeMap map, final int i)
     {
          super.removeAttributesModifiersFromEntity(living, map, i);
          
          performEffect(living, i);
     }
     
     
     
     
     @Override
     public void performEffect (final EntityLivingBase living, final int i)
     {
          living.curePotionEffects(new ItemStack(Item.bucketMilk));
     }
     
     
     
     
     @Override
     public void affectEntity (final EntityLivingBase entity, final EntityLivingBase target, final int i, final double ii)
     {
          performEffect(target, i);
     }
     
     
     
     
     @Override
     public boolean isInstant ()
     {
          return true;
     }
}
