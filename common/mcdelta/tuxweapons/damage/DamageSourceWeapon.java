package mcdelta.tuxweapons.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceWeapon extends EntityDamageSource
{
     public Entity    target;
     public Entity    attacker;
     public ItemStack stack;
     
     
     
     
     public DamageSourceWeapon (final String a, final Entity b, final Entity c, final ItemStack d)
     {
          super(a, b);
          
          this.target = b;
          this.attacker = c;
          this.stack = d;
     }
     
     
     
     
     @Override
     public ChatMessageComponent getDeathMessage (final EntityLivingBase living)
     {
          final ItemStack stack = this.attacker instanceof EntityLivingBase ? ((EntityLivingBase) this.attacker).getHeldItem() : null;
          
          final String s = "death.attack." + this.damageType;
          final String s1 = s + ".item";
          
          return stack != null && stack.hasDisplayName() ? ChatMessageComponent.createFromTranslationWithSubstitutions(s1, new Object[]
          { this.target.getTranslatedEntityName(), this.attacker.getTranslatedEntityName(), stack.getDisplayName() }) : ChatMessageComponent.createFromTranslationWithSubstitutions(s, new Object[]
          { this.target.getTranslatedEntityName(), this.attacker.getTranslatedEntityName() });
     }
     
     
     
     
     @Override
     public Entity getEntity ()
     {
          return this.attacker;
     }
}
