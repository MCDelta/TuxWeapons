package mcdelta.tuxweapons.potions;

public class PotionParalysis extends PotionTW
{
     public PotionParalysis (final String s, final int color, final int x, final int y)
     {
          super(s, true, color, x, y);
          this.setEffectiveness(0.25D);
     }
     
     
     
     
     @Override
     public boolean isReady (final int i, final int i2)
     {
          return true;
     }
}
