package mcdelta.tuxweapons.potions;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;

public class PotionFireAura extends PotionTW
{
     public PotionFireAura (String s, int color, int x, int y)
     {
          super(s, color, x, y);
          this.setEffectiveness(0.5D);
     }
     
     
     
     
     @Override
     public void performEffect (EntityLivingBase entity, int i)
     {
          if (entity != null && entity.boundingBox != null)
          {
               i++;
               
               List<Entity> targets = entity.worldObj.getEntitiesWithinAABB((entity instanceof EntityMob) ? EntityLivingBase.class : EntityMob.class, entity.boundingBox.copy().expand(2 * i, 2 * i, 2 * i));
               
               if (targets != null && !targets.isEmpty())
               {
                    for (int l = 0; l < targets.size(); l++)
                    {
                         if (entity instanceof EntityMob && targets.get(l) instanceof EntityMob)
                         {
                              continue;
                         }
                         
                         if (targets.get(l) != entity)
                         {
                              targets.get(l).setFire(1);
                         }
                    }
               }
          }
     }
     
     
     
     
     @Override
     public boolean isReady (int i, int i2)
     {
          return true;
     }
}
