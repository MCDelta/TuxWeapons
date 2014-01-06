package mcdelta.tuxweapons.support;

import mcdelta.core.support.LimitedModSupport;
import mcdelta.tuxweapons.damage.EnumDamageTypes;

public class TWSupportThaumcraft implements LimitedModSupport
{
     @Override
     public String modid ()
     {
          return "Thaumcraft";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          EnumDamageTypes.SLASHER.addEntities("brainyzombie", "giantbrainyzombie", "thaumslime", "taintspider", "taintedchicken", "taintedcow", "taintedpig", "taintedsheep", "taintedvillager");
          EnumDamageTypes.BASHER.addEntities("wisp", "firebat", "taintacle", "taintswarm", "taintedcreeper", "pech");
     }
}
