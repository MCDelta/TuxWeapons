package mcdelta.tuxweapons.specials.potions;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.MinecraftForgeClient;

public class PotionConfusion extends PotionTW
{
     public PotionConfusion (String s, int color, int x, int y)
     {
          super(s, true, color, x, y);
     }
}
