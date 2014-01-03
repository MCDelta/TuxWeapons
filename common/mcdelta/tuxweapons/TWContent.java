package mcdelta.tuxweapons;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ItemMaterial;
import mcdelta.tuxweapons.block.BlockBrewStand;
import mcdelta.tuxweapons.block.BlockRedstoneTempBlock;
import mcdelta.tuxweapons.block.tileentity.TileBrewStand;
import mcdelta.tuxweapons.entity.EntityBolt;
import mcdelta.tuxweapons.entity.EntityDynamite;
import mcdelta.tuxweapons.entity.EntityEMPGrenade;
import mcdelta.tuxweapons.entity.EntityGrappHook;
import mcdelta.tuxweapons.entity.EntityKnife;
import mcdelta.tuxweapons.entity.EntitySpear;
import mcdelta.tuxweapons.entity.EntityTWFireball;
import mcdelta.tuxweapons.item.ItemBattleaxe;
import mcdelta.tuxweapons.item.ItemBolt;
import mcdelta.tuxweapons.item.ItemCrossbow;
import mcdelta.tuxweapons.item.ItemDynamite;
import mcdelta.tuxweapons.item.ItemEMPGrenade;
import mcdelta.tuxweapons.item.ItemFireChargeCannon;
import mcdelta.tuxweapons.item.ItemGrappHook;
import mcdelta.tuxweapons.item.ItemHammer;
import mcdelta.tuxweapons.item.ItemKnife;
import mcdelta.tuxweapons.item.ItemMace;
import mcdelta.tuxweapons.item.ItemShield;
import mcdelta.tuxweapons.item.ItemSpear;
import mcdelta.tuxweapons.item.ItemTW;
import mcdelta.tuxweapons.item.ItemTechnical;
import mcdelta.tuxweapons.item.ItemWhock;
import mcdelta.tuxweapons.item.ItemWhockCrafter;
import mcdelta.tuxweapons.specials.enchant.EnchDrawback;
import mcdelta.tuxweapons.specials.enchant.EnchEXP;
import mcdelta.tuxweapons.specials.enchant.EnchStrike;
import mcdelta.tuxweapons.specials.enchant.EnchSwift;
import mcdelta.tuxweapons.specials.enchant.EnchVenom;
import mcdelta.tuxweapons.specials.enchant.EnchantmentTW;
import mcdelta.tuxweapons.specials.potions.Potions;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class TWContent
{
    public static Map<ItemMaterial, ItemBattleaxe> battleaxes = new HashMap<ItemMaterial, ItemBattleaxe>();
    public static Map<ItemMaterial, ItemHammer> hammers = new HashMap<ItemMaterial, ItemHammer>();
    public static Map<ItemMaterial, ItemSpear> spears = new HashMap<ItemMaterial, ItemSpear>();
    public static Map<ItemMaterial, ItemMace> maces = new HashMap<ItemMaterial, ItemMace>();
    public static Map<ItemMaterial, ItemKnife> knives = new HashMap<ItemMaterial, ItemKnife>();
    public static Map<ItemMaterial, ItemGrappHook> grappHooks = new HashMap<ItemMaterial, ItemGrappHook>();
    public static Map<ItemMaterial, ItemShield> shields = new HashMap<ItemMaterial, ItemShield>();
    public static Map<ItemMaterial, ItemWhock> whocks = new HashMap<ItemMaterial, ItemWhock>();
    public static Map<ItemMaterial, ItemWhockCrafter> whockCrafters = new HashMap<ItemMaterial, ItemWhockCrafter>();

    public static ItemFireChargeCannon fireChargeCannon = new ItemFireChargeCannon();
    public static ItemCrossbow crossBow = new ItemCrossbow();
    public static ItemBolt bolt = new ItemBolt();
    public static ItemDynamite dynamite = new ItemDynamite();
    public static ItemEMPGrenade empGrenade = new ItemEMPGrenade();
    public static ItemTechnical technical = new ItemTechnical();
    public static ItemTW magmaCore = (ItemTW) new ItemTW("magmaCore").setCreativeTab(CreativeTabs.tabMaterials);

    public static BlockRedstoneTempBlock redstoneTmpBlock;
    public static BlockBrewStand brewStandMCD;

    public static EnchSwift swift;
    public static EnchStrike strike;
    public static EnchVenom venom;
    public static EnchDrawback drawback;
    public static EnchEXP expIncrease;
    public static EnchantmentTW hardened;

    public static void load()
    {
        fireChargeCannon = new ItemFireChargeCannon();
        crossBow = new ItemCrossbow();
        bolt = new ItemBolt();
        dynamite = new ItemDynamite();
        empGrenade = new ItemEMPGrenade();
        technical = new ItemTechnical();
        magmaCore = (ItemTW) new ItemTW("magmaCore").setCreativeTab(CreativeTabs.tabMaterials);

        for (ItemMaterial mat : MaterialRegistry.materials())
        {
            if (mat.needsWeapons())
            {
                ItemBattleaxe battleaxe = new ItemBattleaxe(mat);
                battleaxes.put(mat, battleaxe);

                ItemMace mace = new ItemMace(mat);
                maces.put(mat, mace);

                ItemHammer hammer = new ItemHammer(mat);
                hammers.put(mat, hammer);

                ItemKnife knife = new ItemKnife(mat);
                knives.put(mat, knife);

                ItemSpear spear = new ItemSpear(mat);
                spears.put(mat, spear);

                ItemGrappHook grappHook = new ItemGrappHook(mat);
                grappHooks.put(mat, grappHook);

                ItemShield shield = new ItemShield(mat);
                shields.put(mat, shield);

                ItemWhock whock = new ItemWhock(mat);
                whocks.put(mat, whock);

                ItemWhockCrafter whockCrafter = new ItemWhockCrafter(mat);
                whockCrafters.put(mat, whockCrafter);
            }
        }

        redstoneTmpBlock = new BlockRedstoneTempBlock();
        Block.blocksList[117] = null;
        brewStandMCD = (BlockBrewStand) new BlockBrewStand(117).setHardness(0.5F).setLightValue(0.125F).setUnlocalizedName("brewingStand").setTextureName("brewing_stand");
        GameRegistry.registerBlock(brewStandMCD, "brewStandMCD");
        GameRegistry.registerTileEntity(TileBrewStand.class, "tileBrewStandMCD");

        swift = new EnchSwift("swift", 2, EnumEnchantmentType.weapon);
        strike = new EnchStrike("strike", 3, EnumEnchantmentType.weapon);
        venom = new EnchVenom("venom", 5, EnumEnchantmentType.weapon);
        drawback = new EnchDrawback("drawback", 5, EnumEnchantmentType.bow);
        expIncrease = new EnchEXP("expIncrease", 4, EnumEnchantmentType.weapon);
        hardened = new EnchantmentTW("hardened", 2, EnumEnchantmentType.armor_torso).setMinMax(15, 30);

        EntityRegistry.registerModEntity(EntitySpear.class, TuxWeapons.MOD_ID.toLowerCase() + ":" + "spear", 1, TuxWeapons.instance, 64, 3, true);
        EntityRegistry.registerModEntity(EntityKnife.class, TuxWeapons.MOD_ID.toLowerCase() + ":" + "knife", 2, TuxWeapons.instance, 64, 3, true);
        EntityRegistry.registerModEntity(EntityGrappHook.class, TuxWeapons.MOD_ID.toLowerCase() + ":" + "grappHook", 3, TuxWeapons.instance, 64, 3, true);
        EntityRegistry.registerModEntity(EntityTWFireball.class, TuxWeapons.MOD_ID.toLowerCase() + ":" + "fireball", 4, TuxWeapons.instance, 64, 3, true);
        EntityRegistry.registerModEntity(EntityBolt.class, TuxWeapons.MOD_ID.toLowerCase() + ":" + "bolt", 5, TuxWeapons.instance, 64, 3, true);
        EntityRegistry.registerModEntity(EntityDynamite.class, TuxWeapons.MOD_ID.toLowerCase() + ":" + "dynamite", 6, TuxWeapons.instance, 64, 3, true);
        EntityRegistry.registerModEntity(EntityEMPGrenade.class, TuxWeapons.MOD_ID.toLowerCase() + ":" + "empGrenade", 7, TuxWeapons.instance, 64, 3, true);

        Potions.init();
    }
}
