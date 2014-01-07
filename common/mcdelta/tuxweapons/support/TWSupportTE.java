package mcdelta.tuxweapons.support;

import mcdelta.core.support.ILimitedModSupport;
import mcdelta.tuxweapons.damage.EnumDamageTypes;

public class TWSupportTE implements ILimitedModSupport
{
     @Override
     public String modid ()
     {
          return "ThermalExpansion";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          EnumDamageTypes.BASHER.addEntities("blizz");
     }
}
