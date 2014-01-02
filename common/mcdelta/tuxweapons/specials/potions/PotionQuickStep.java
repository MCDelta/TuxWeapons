package mcdelta.tuxweapons.specials.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionQuickStep extends PotionTW
{
     public PotionQuickStep (String s, int color, int x, int y)
     {
          super(s, color, x, y);
          this.setEffectiveness(1.5D);
     }
}
