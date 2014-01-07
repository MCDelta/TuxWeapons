package mcdelta.tuxweapons.support;

import mcdelta.core.support.ILimitedModSupport;
import mcdelta.tuxweapons.damage.EnumDamageTypes;

public class TWSupportTwilightForest implements ILimitedModSupport
{
     @Override
     public String modid ()
     {
          return "TwilightForest";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          EnumDamageTypes.SLASHER.addEntities("twilightforest.redcap", "twilightforest.swarm spider", "twilightforest.hostile wolf", "twilightforest.hedge spider", "twilightforest.twilight kobold", "twilightforest.death tome", "twilightforest.slime beetle", "twilightforest.minotaur", "twilightforest.redcap sapper", "twilightforest.mist wolf", "twilightforest.king spider", "twilightforest.mini ghast", "twilightforest.redscale broodling", "twilightforest.lower goblin knight", "twilightforest.helmet crab");
          EnumDamageTypes.BASHER.addEntities("twilightforest.naga", "twilightforest.skeleton druid", "twilightforest.twilight wraith", "twilightforest.hydra", "twilightforest.mosquito swarm", "twilightforest.minoshroom", "twilightforest.fire beetle", "twilightforest.pinch beetle", "twilightforest.maze slime", "twilightforest.tower golem", "twilightforest.tower ghast", "twilightforest.tower termite", "twilightforest.block&chain goblin", "twilightforest.upper goblin knight", "twilightforest.knight phantom");
     }
}
