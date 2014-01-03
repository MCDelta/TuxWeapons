package mcdelta.tuxweapons.specials.potions;

public class PotionParalysis extends PotionTW
{
    public PotionParalysis(String s, int color, int x, int y)
    {
        super(s, true, color, x, y);
        setEffectiveness(0.25D);
    }

    @Override
    public boolean isReady(int i, int i2)
    {
        return true;
    }
}
