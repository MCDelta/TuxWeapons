package mcdelta.tuxweapons.entity;

import java.util.Iterator;
import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.item.ItemWeapon;
import mcdelta.tuxweapons.damage.DamageSourceWeapon;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntitySpear extends EntityTWThrowable
{
     public EntitySpear (World world)
     {
          super(world);
     }
     
     
     
     
     public EntitySpear (World world, EntityLivingBase living, float charge, ItemStack item)
     {
          super(world, living, charge, item);
     }
}
