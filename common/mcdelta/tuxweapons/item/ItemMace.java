package mcdelta.tuxweapons.item;

import java.util.List;

import com.google.common.collect.Multimap;

import mcdelta.core.EnumMCDMods;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ToolMaterial;
import mcdelta.core.proxy.ClientProxy;
import mcdelta.tuxweapons.TuxWeaponsCore;
import mcdelta.tuxweapons.damage.DamageModifier;
import mcdelta.tuxweapons.damage.DamageSourceWeapon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemMace extends ItemWeapon
{
     
     public ItemMace (ToolMaterial mat)
     {
          super("mace", EnumMCDMods.TUXWEAPONS, mat, 1.0F);
          this.setMaxDamage((int) ((float) mat.getMaxUses() * 0.9F));
     }
}
