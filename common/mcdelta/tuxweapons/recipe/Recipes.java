package mcdelta.tuxweapons.recipe;

import mcdelta.core.material.ToolMaterial;
import mcdelta.tuxweapons.item.ItemTW;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{
     public static void addCraftingRecipes ()
     {
          ItemStack wood = new ItemStack(Block.planks);
          ItemStack iron = new ItemStack(Item.ingotIron);
          ItemStack string = new ItemStack(Item.silk);
          ItemStack feather = new ItemStack(Item.feather);
          ItemStack gunpowder = new ItemStack(Item.gunpowder);
          ItemStack sand = new ItemStack(Block.sand);
          ItemStack redStone = new ItemStack(Item.redstone);
          ItemStack clay = new ItemStack(Item.clay);
          
          GameRegistry.addRecipe(new ItemStack(ItemTW.crossBow), "xxy", "syx", "ysx", 'x', wood, 'y', iron, 's', string);
          GameRegistry.addRecipe(new ItemStack(ItemTW.crossBow), "yxx", "xys", "xsy", 'x', wood, 'y', iron, 's', string);
          GameRegistry.addRecipe(new ItemStack(ItemTW.bolt, 3), " i ", " i ", " f ", 'i', iron, 'f', feather);
          GameRegistry.addRecipe(new ItemStack(ItemTW.dynamite, 2), " gs", " x ", " g ", 'g', gunpowder, 'x', sand, 's', string);
          GameRegistry.addRecipe(new ItemStack(ItemTW.empGrenade, 3), " x ", "xox", " x ", 'x', redStone, 'o', clay);
          GameRegistry.addRecipe(new RecipePotionBolt());
          
          for (ToolMaterial mat : ToolMaterial.mats)
          {
               String material = mat.getOreDictionaryName();
               
               if (mat.needsWeapons())
               {
                    // Battleaxe
                    ItemStack battleaxe = new ItemStack(ItemTW.battleaxes.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(battleaxe, "xxx", "xox", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Hammer
                    ItemStack hammer = new ItemStack(ItemTW.hammers.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(hammer, "xox", "xox", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Knife
                    ItemStack knife = new ItemStack(ItemTW.knives.get(mat), 4);
                    GameRegistry.addRecipe(new ShapedOreRecipe(knife, " x", "o ", 'x', material, 'o', "stickWood"));
                    
                    // Spear
                    ItemStack spear = new ItemStack(ItemTW.spears.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(spear, "  x", " o ", "o  ", 'x', material, 'o', "stickWood"));
                    
                    // Grappling Hook
                    ItemStack grappHook = new ItemStack(ItemTW.grappHooks.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(grappHook, " xx", " ox", "o  ", 'x', material, 'o', "ingotIron"));
                    
                    // Shield
                    ItemStack shield = new ItemStack(ItemTW.shields.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(shield, "oxo", "xox", "oxo", 'x', material, 'o', "plankWood"));
                    
                    // Whock Crafter
                    ItemStack whockCrafter = new ItemStack(ItemTW.whockCrafters.get(mat), 1, mat.getMaxUses() - 1);
                    GameRegistry.addRecipe(new ShapedOreRecipe(whockCrafter, "xxx", "xox", " x ", 'x', material, 'o', Block.obsidian));
               }
          }
     }
}
