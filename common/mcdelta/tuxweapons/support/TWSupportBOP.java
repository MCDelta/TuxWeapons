package mcdelta.tuxweapons.support;

import mcdelta.core.support.ILimitedModSupport;
import mcdelta.tuxweapons.damage.EnumDamageTypes;

public class TWSupportBOP implements ILimitedModSupport
{
     @Override
     public String modid ()
     {
          return "BiomesOPlenty";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          EnumDamageTypes.SLASHER.addEntities("biomesoplenty.junglespider", "biomesoplenty.glob");
          EnumDamageTypes.BASHER.addEntities("biomesoplenty.phantom", "biomesoplenty.wasp");
     }
}
