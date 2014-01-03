package mcdelta.tuxweapons;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.IContent;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.tuxweapons.block.BlockBrewStand;
import mcdelta.tuxweapons.block.BlockRedstoneTempBlock;
import mcdelta.tuxweapons.block.tileentity.TileBrewStand;
import mcdelta.tuxweapons.enchant.EnchDrawback;
import mcdelta.tuxweapons.enchant.EnchEXP;
import mcdelta.tuxweapons.enchant.EnchStrike;
import mcdelta.tuxweapons.enchant.EnchSwift;
import mcdelta.tuxweapons.enchant.EnchVenom;
import mcdelta.tuxweapons.enchant.EnchantmentTW;
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
import mcdelta.tuxweapons.potions.Potions;
import mcdelta.tuxweapons.recipe.RecipePotionBolt;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class TWContent implements IContent
{
     public static Map<ItemMaterial, ItemBattleaxe>    battleaxes    = new HashMap<ItemMaterial, ItemBattleaxe>();
     public static Map<ItemMaterial, ItemHammer>       hammers       = new HashMap<ItemMaterial, ItemHammer>();
     public static Map<ItemMaterial, ItemSpear>        spears        = new HashMap<ItemMaterial, ItemSpear>();
     public static Map<ItemMaterial, ItemMace>         maces         = new HashMap<ItemMaterial, ItemMace>();
     public static Map<ItemMaterial, ItemKnife>        knives        = new HashMap<ItemMaterial, ItemKnife>();
     public static Map<ItemMaterial, ItemGrappHook>    grappHooks    = new HashMap<ItemMaterial, ItemGrappHook>();
     public static Map<ItemMaterial, ItemShield>       shields       = new HashMap<ItemMaterial, ItemShield>();
     public static Map<ItemMaterial, ItemWhock>        whocks        = new HashMap<ItemMaterial, ItemWhock>();
     public static Map<ItemMaterial, ItemWhockCrafter> whockCrafters = new HashMap<ItemMaterial, ItemWhockCrafter>();
     
     public static ItemFireChargeCannon                fireChargeCannon;
     public static ItemCrossbow                        crossBow;
     public static ItemBolt                            bolt;
     public static ItemDynamite                        dynamite;
     public static ItemEMPGrenade                      empGrenade;
     public static ItemTechnical                       technical;
     public static ItemTW                              magmaCore;
     
     public static BlockRedstoneTempBlock              redstoneTmpBlock;
     public static BlockBrewStand                      brewStandMCD;
     
     public static EnchSwift                           swift;
     public static EnchStrike                          strike;
     public static EnchVenom                           venom;
     public static EnchDrawback                        drawback;
     public static EnchEXP                             expIncrease;
     public static EnchantmentTW                       hardened;
     
     
     
     
     @Override
     public void addContent ()
     {
          fireChargeCannon = new ItemFireChargeCannon();
          crossBow = new ItemCrossbow();
          bolt = new ItemBolt();
          dynamite = new ItemDynamite();
          empGrenade = new ItemEMPGrenade();
          technical = new ItemTechnical();
          magmaCore = (ItemTW) new ItemTW("magmaCore").setCreativeTab(CreativeTabs.tabMaterials);
          
          Block.blocksList[117] = null;
          brewStandMCD = (BlockBrewStand) new BlockBrewStand(117).setHardness(0.5F).setLightValue(0.125F).setUnlocalizedName("brewingStand").setTextureName("brewing_stand");
          GameRegistry.registerBlock(brewStandMCD, "brewStandMCD");
          GameRegistry.registerTileEntity(TileBrewStand.class, "tileBrewStandMCD");
          redstoneTmpBlock = new BlockRedstoneTempBlock();
          
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
     
     
     
     
     @Override
     public void addMaterialBasedContent (final ItemMaterial mat)
     {
          if (mat.needsWeapons())
          {
               final ItemBattleaxe battleaxe = new ItemBattleaxe(mat);
               battleaxes.put(mat, battleaxe);
               
               final ItemMace mace = new ItemMace(mat);
               maces.put(mat, mace);
               
               final ItemHammer hammer = new ItemHammer(mat);
               hammers.put(mat, hammer);
               
               final ItemKnife knife = new ItemKnife(mat);
               knives.put(mat, knife);
               
               final ItemSpear spear = new ItemSpear(mat);
               spears.put(mat, spear);
               
               final ItemGrappHook grappHook = new ItemGrappHook(mat);
               grappHooks.put(mat, grappHook);
               
               final ItemShield shield = new ItemShield(mat);
               shields.put(mat, shield);
               
               final ItemWhock whock = new ItemWhock(mat);
               whocks.put(mat, whock);
               
               final ItemWhockCrafter whockCrafter = new ItemWhockCrafter(mat);
               whockCrafters.put(mat, whockCrafter);
          }
     }
     
     
     
     
     @Override
     public void addRecipes ()
     {
          final ItemStack wood = new ItemStack(Block.planks);
          final ItemStack iron = new ItemStack(Item.ingotIron);
          final ItemStack string = new ItemStack(Item.silk);
          final ItemStack feather = new ItemStack(Item.feather);
          final ItemStack gunpowder = new ItemStack(Item.gunpowder);
          final ItemStack sand = new ItemStack(Block.sand);
          final ItemStack redStone = new ItemStack(Item.redstone);
          final ItemStack clay = new ItemStack(Item.clay);
          
          GameRegistry.addRecipe(new ItemStack(TWContent.crossBow), "xxy", "syx", "ysx", 'x', wood, 'y', iron, 's', string);
          GameRegistry.addRecipe(new ItemStack(TWContent.crossBow), "yxx", "xys", "xsy", 'x', wood, 'y', iron, 's', string);
          GameRegistry.addRecipe(new ItemStack(TWContent.bolt, 3), " i ", " i ", " f ", 'i', iron, 'f', feather);
          GameRegistry.addRecipe(new ItemStack(TWContent.dynamite, 2), " gs", " x ", " g ", 'g', gunpowder, 'x', sand, 's', string);
          GameRegistry.addRecipe(new ItemStack(TWContent.empGrenade, 3), " x ", "xox", " x ", 'x', redStone, 'o', clay);
          GameRegistry.addRecipe(new RecipePotionBolt());
          
          for (final ItemMaterial mat : MaterialRegistry.materials())
          {
               final String material = mat.oreName();
               
               if (mat.needsWeapons())
               {
                    // Battleaxe
                    final ItemStack battleaxe = new ItemStack(TWContent.battleaxes.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(battleaxe, "xxx", "xox", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Hammer
                    final ItemStack hammer = new ItemStack(TWContent.hammers.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(hammer, "xox", "xox", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Knife
                    final ItemStack knife = new ItemStack(TWContent.knives.get(mat), 4);
                    GameRegistry.addRecipe(new ShapedOreRecipe(knife, " x", "o ", 'x', material, 'o', "stickWood"));
                    
                    // Spear
                    final ItemStack spear = new ItemStack(TWContent.spears.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(spear, "  x", " o ", "o  ", 'x', material, 'o', "stickWood"));
                    
                    // Grappling Hook
                    final ItemStack grappHook = new ItemStack(TWContent.grappHooks.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(grappHook, " xx", " ox", "o  ", 'x', material, 'o', "ingotIron"));
                    
                    // Shield
                    final ItemStack shield = new ItemStack(TWContent.shields.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(shield, "oxo", "xox", "oxo", 'x', material, 'o', "plankWood"));
                    
                    // Whock Crafter
                    final ItemStack whockCrafter = new ItemStack(TWContent.whockCrafters.get(mat), 1, mat.maxUses() - 1);
                    GameRegistry.addRecipe(new ShapedOreRecipe(whockCrafter, "xxx", "xox", " x ", 'x', material, 'o', Block.obsidian));
               }
          }
     }
}
