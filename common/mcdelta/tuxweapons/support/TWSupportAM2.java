package mcdelta.tuxweapons.support;

import mcdelta.core.support.ILimitedModSupport;
import mcdelta.tuxweapons.damage.EnumDamageTypes;

public class TWSupportAM2 implements ILimitedModSupport
{
     
     @Override
     public String modid ()
     {
          return "arsmagica2";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          EnumDamageTypes.SLASHER.addEntities("arsmagica2.mobdryad", "arsmagica2.mobhecate", "arsmagica2.moblightmage", "arsmagica2.mobdarkmage", "arsmagica2.mobfireelemental", "arsmagica2.mobdarkling", "arsmagica2.bossnatureguardian", "arsmagica2.bossarcaneguardian");
          EnumDamageTypes.BASHER.addEntities("arsmagica2.mobmanacreeper", "arsmagica2.mobmanaelemental", "arsmagica2.hellcow", "arsmagica2.mobwaterelemental", "arsmagica2.earthelemental", "arsmagica2.bossearthguardian", "arsmagica2.bosswaterguardian", "arsmagica2.bosswinterguardian", "arsmagica2.bossfireguardian", "arsmagica2.bossairguardian");
     }
     
}
