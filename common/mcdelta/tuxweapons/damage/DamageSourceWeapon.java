package mcdelta.tuxweapons.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceWeapon extends EntityDamageSource
{
    public Entity target;
    public Entity attacker;
    public ItemStack stack;

    public DamageSourceWeapon(String a, Entity b, Entity c, ItemStack d)
    {
        super(a, b);

        target = b;
        attacker = c;
        stack = d;
    }

    @Override
    public ChatMessageComponent getDeathMessage(EntityLivingBase living)
    {
        ItemStack stack = attacker instanceof EntityLivingBase ? ((EntityLivingBase) attacker).getHeldItem() : null;

        String s = "death.attack." + damageType;
        String s1 = s + ".item";

        return (stack != null) && stack.hasDisplayName() ? ChatMessageComponent.createFromTranslationWithSubstitutions(s1, new Object[]
        { target.getTranslatedEntityName(), attacker.getTranslatedEntityName(), stack.getDisplayName() }) : ChatMessageComponent.createFromTranslationWithSubstitutions(s,
                new Object[]
                { target.getTranslatedEntityName(), attacker.getTranslatedEntityName() });
    }

    @Override
    public Entity getEntity()
    {
        return attacker;
    }
}
