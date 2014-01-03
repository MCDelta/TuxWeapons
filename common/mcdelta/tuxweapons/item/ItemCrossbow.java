package mcdelta.tuxweapons.item;

import mcdelta.core.assets.Assets;
import mcdelta.core.item.ItemDeltaBow;
import mcdelta.tuxweapons.TWContent;
import mcdelta.tuxweapons.TuxWeapons;
import mcdelta.tuxweapons.entity.EntityBolt;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrossbow extends ItemDeltaBow
{
    @SideOnly(Side.CLIENT)
    private Icon bowPull_0;

    @SideOnly(Side.CLIENT)
    private Icon bowPull_1;

    @SideOnly(Side.CLIENT)
    private Icon bowPull_2;

    @SideOnly(Side.CLIENT)
    private Icon bowPull_3;

    @SideOnly(Side.CLIENT)
    private Icon bowPull_4;

    public ItemCrossbow()
    {
        super(TuxWeapons.instance, "crossBow", new Item[]
        { TWContent.bolt });
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(TuxWeapons.MOD_ID + ":" + name);
        bowPull_0 = iconRegister.registerIcon(TuxWeapons.MOD_ID + ":" + "crossBow_pull_0");
        bowPull_1 = iconRegister.registerIcon(TuxWeapons.MOD_ID + ":" + "crossBow_pull_1");
        bowPull_2 = iconRegister.registerIcon(TuxWeapons.MOD_ID + ":" + "crossBow_pull_2");
        bowPull_3 = iconRegister.registerIcon(TuxWeapons.MOD_ID + ":" + "crossBow_pull_3");
        bowPull_4 = iconRegister.registerIcon(TuxWeapons.MOD_ID + ":" + "crossBow_pull_4");
    }

    @Override
    public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if (stack.stackTagCompound == null)
        {
            stack.setTagCompound(new NBTTagCompound());
            stack.stackTagCompound.setBoolean("Loaded", false);
        }

        NBTTagCompound tagCompound = stack.getTagCompound();

        if ((player.getItemInUse() == null) && (tagCompound.getBoolean("Loaded") == false))
        {
            return itemIcon;
        }

        if ((player.getItemInUse() == null) && (tagCompound.getBoolean("Loaded") == true))
        {
            return bowPull_1;
        }

        int pulling = stack.getMaxItemUseDuration() - useRemaining;

        if (pulling >= 24)
        {
            return bowPull_4;
        }

        else if (pulling > 19)
        {
            return bowPull_3;
        }

        else if (pulling > 6)
        {
            return bowPull_2;
        }

        if (tagCompound.getBoolean("Loaded") == true)
        {
            return bowPull_1;
        }

        else
        {
            return bowPull_0;
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4)
    {
        int charge = getMaxItemUseDuration(stack) - par4;

        boolean infinity = player.capabilities.isCreativeMode || (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0);

        if (infinity || player.inventory.hasItem(itemID))
        {
            float speed;

            if (charge >= 27)
            {
                speed = 200 / 50;
            }

            else
            {
                speed = 0.5F;
            }

            NBTTagCompound tagCompound = stack.getTagCompound();

            EntityBolt bolt = new EntityBolt(world, player, speed, tagCompound.getTagList("BoltEffects"));
            tagCompound.removeTag("BoltEffects");

            int punchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (punchLvl > 0)
            {
                bolt.setKnockbackStrength(punchLvl);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                bolt.setFire(100);
                bolt.hasFire = true;
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, (1.0F / ((itemRand.nextFloat() * 0.4F) + 1.2F)) + (charge * 0.5F));

            if (Assets.isServer())
            {
                world.spawnEntityInWorld(bolt);
            }

            stack.stackTagCompound.setBoolean("Loaded", false);
            stack.damageItem(1, player);
        }
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (stack.stackTagCompound == null)
        {
            stack.setTagCompound(new NBTTagCompound());
            stack.stackTagCompound.setBoolean("Loaded", false);
        }

        NBTTagCompound tagCompound = stack.getTagCompound();

        if ((tagCompound.getBoolean("Loaded") == true) || player.capabilities.isCreativeMode)
        {
            ArrowNockEvent event = new ArrowNockEvent(player, stack);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled())
            {
                return event.result;
            }

            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        }

        return stack;
    }

    @Override
    public int getItemEnchantability()
    {
        return 1;
    }

    public static void load(ItemStack stack, NBTTagList tagList)
    {
        NBTTagCompound tagCompound = stack.getTagCompound();

        if (tagCompound.getBoolean("Loaded") == false)
        {
            tagCompound.setBoolean("Loaded", true);
        }

        tagCompound.setTag("BoltEffects", tagList);
    }

    public static void setNBT(Boolean b, ItemStack stack)
    {
        stack.setTagCompound(new NBTTagCompound());
        stack.stackTagCompound.setBoolean("Loaded", false);
    }

    @Override
    public boolean isFull3D()
    {
        return true;
    }
}
