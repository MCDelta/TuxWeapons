package mcdelta.tuxweapons.potions;

import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionTW extends Potion
{
     public static PotionFlight    flight    = new PotionFlight("flight", 0xe9ab17, 3, 0);
     public static PotionConfusion confusion = new PotionConfusion("confusion", 0x0f8775, 4, 0);
     public static PotionParalysis paralysis = new PotionParalysis("paralysis", 0xf6fe73, 5, 0);
     public static PotionFireAura  fireAura  = new PotionFireAura("fireAura", 0xc42525, 1, 0);
     public static PotionQuickStep quickStep = new PotionQuickStep("quickStep", 0x605642, 2, 0);
     public static PotionCure      cure      = new PotionCure("cure", 0xe5e5e5, 0, 0);
     
     
     
     
     public PotionTW (final String s, final int color, final int x, final int y)
     {
          this(s, false, color, x, y);
     }
     
     
     
     
     protected PotionTW (final String s, final boolean bad, final int color, final int x, final int y)
     {
          super(TuxWeapons.instance.config().getPotionID(s), bad, color);
          setPotionName("potion." + TuxWeapons.instance.id().toLowerCase() + ":" + s);
          setIconIndex(x, y);
     }
     
     
     
     
     @Override
     public Potion setEffectiveness (final double i)
     {
          return super.setEffectiveness(i);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public int getStatusIconIndex ()
     {
          Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("tuxweapons:textures/gui/potionEffects.png"));
          return super.getStatusIconIndex();
     }
}
