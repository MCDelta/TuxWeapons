package mcdelta.tuxweapons.support;

import mcdelta.core.support.LimitedModSupport;
import mcdelta.tuxweapons.damage.EnumDamageTypes;

public class TWSupportBOP implements LimitedModSupport
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
