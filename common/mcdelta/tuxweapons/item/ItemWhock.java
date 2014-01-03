package mcdelta.tuxweapons.item;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.Position;
import mcdelta.core.item.ItemDeltaTool;
import mcdelta.core.material.ToolMaterial;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet53BlockChange;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ItemWhock extends ItemDeltaTool
{
     public ItemWhock (ToolMaterial mat)
     {
          super(TuxWeapons.instance, "whock", mat, ItemPickaxe.blocksEffectiveAgainst, 2.0F, false);
     }
     
     
     
     
     public boolean onEntitySwing (EntityLivingBase living, ItemStack stack)
     {
          return false;
     }
     
     
     
     
     public boolean onBlockDestroyed (ItemStack stack, World world, int id, int x, int y, int z, EntityLivingBase living)
     {
          if (living instanceof EntityPlayer)
          {
               MovingObjectPosition moving = getMovingObjectPositionFromPlayer(world, (EntityPlayer) living, false);
               Position hitBlock = new Position(world, x, y, z);
               
               if (hitBlock != null && moving != null && moving.typeOfHit == EnumMovingObjectType.TILE)
               {
                    List<Position> positions = getPositionsFromSide(hitBlock, moving.sideHit);
                    
                    for (Position pos : positions)
                    {
                         if (!Assets.isAirBlock(pos))
                         {
                              if (living instanceof EntityPlayerMP)
                              {
                                   if (Block.blocksList[living.worldObj.getBlockId(pos.x, pos.y, pos.z)] != null)
                                   {
                                        tryHarvestBlock(world, (EntityPlayerMP) living, pos.x, pos.y, pos.z);
                                   }
                              }
                         }
                    }
               }
          }
          
          return super.onBlockDestroyed(stack, world, id, x, y, z, living);
     }
     
     
     
     
     private boolean tryHarvestBlock (World world, EntityPlayerMP player, int x, int y, int z)
     {
          BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x, y, z);
          if (event.isCanceled())
          {
               return false;
          }
          else
          {
               int l = world.getBlockId(x, y, z);
               int i1 = world.getBlockMetadata(x, y, z);
               world.playAuxSFXAtEntity(player, 2001, x, y, z, l + (world.getBlockMetadata(x, y, z) << 12));
               boolean flag = false;
               
               if (player.theItemInWorldManager.isCreative())
               {
                    flag = removeBlock(world, player, x, y, z);
                    player.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(x, y, z, world));
               }
               else
               {
                    ItemStack itemstack = player.getCurrentEquippedItem();
                    boolean flag1 = false;
                    Block block = Block.blocksList[l];
                    if (block != null)
                    {
                         flag1 = block.canHarvestBlock(player, i1);
                    }
                    
                    if (itemstack != null)
                    {
                         if (itemstack.stackSize == 0)
                         {
                              player.destroyCurrentEquippedItem();
                         }
                    }
                    
                    flag = removeBlock(world, player, x, y, z);
                    if (flag && flag1)
                    {
                         Block.blocksList[l].harvestBlock(world, player, x, y, z, i1);
                    }
               }
               
               // Drop experience
               if (!player.theItemInWorldManager.isCreative() && flag && event != null)
               {
                    Block.blocksList[l].dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
               }
               
               return flag;
          }
     }
     
     
     
     
     private boolean removeBlock (World world, EntityPlayerMP player, int x, int y, int z)
     {
          Block block = Block.blocksList[world.getBlockId(x, y, z)];
          int l = world.getBlockMetadata(x, y, z);
          
          if (block != null)
          {
               block.onBlockHarvested(world, x, y, z, l, player);
          }
          
          boolean flag = (block != null && block.removeBlockByPlayer(world, player, x, y, z));
          
          if (block != null && flag)
          {
               block.onBlockDestroyedByPlayer(world, x, y, z, l);
          }
          
          return flag;
     }
     
     
     
     
     private List<Position> getPositionsFromSide (Position pos, int side)
     {
          List<Position> positions = new ArrayList<Position>();
          
          switch (side)
          {
               case 0:
               case 1:
                    positions.add(pos.move(1, 0, 0));
                    positions.add(pos.move(1, 0, 1));
                    positions.add(pos.move(0, 0, 1));
                    positions.add(pos.move(-1, 0, 0));
                    positions.add(pos.move(-1, 0, -1));
                    positions.add(pos.move(0, 0, -1));
                    positions.add(pos.move(1, 0, -1));
                    positions.add(pos.move(-1, 0, 1));
                    break;
               case 2:
               case 3:
                    positions.add(pos.move(1, 0, 0));
                    positions.add(pos.move(1, 1, 0));
                    positions.add(pos.move(0, 1, 0));
                    positions.add(pos.move(-1, 0, 0));
                    positions.add(pos.move(-1, -1, 0));
                    positions.add(pos.move(0, -1, 0));
                    positions.add(pos.move(1, -1, 0));
                    positions.add(pos.move(-1, 1, 0));
                    break;
               case 4:
               case 5:
                    positions.add(pos.move(0, 1, 0));
                    positions.add(pos.move(0, 1, 1));
                    positions.add(pos.move(0, 0, 1));
                    positions.add(pos.move(0, 0, -1));
                    positions.add(pos.move(0, -1, -1));
                    positions.add(pos.move(0, -1, 0));
                    positions.add(pos.move(0, -1, 1));
                    positions.add(pos.move(0, 1, -1));
                    break;
               default:
                    break;
          }
          
          return positions;
     }
     
     
     
     
     public boolean canHarvestBlock (Block block)
     {
          return block == Block.obsidian ? toolMaterial.getHarvestLevel() == 3 : (block != Block.blockDiamond && block != Block.oreDiamond ? (block != Block.oreEmerald && block != Block.blockEmerald ? (block != Block.blockGold && block != Block.oreGold ? (block != Block.blockIron && block != Block.oreIron ? (block != Block.blockLapis && block != Block.oreLapis ? (block != Block.oreRedstone && block != Block.oreRedstoneGlowing ? (block.blockMaterial == Material.rock ? true : (block.blockMaterial == Material.iron ? true : block.blockMaterial == Material.anvil)) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2);
     }
     
     
     
     
     @Override
     public float getStrVsBlock (ItemStack stack, Block block, int meta)
     {
          float strength = super.getStrVsBlock(stack, block, meta);
          return strength / 4F;
     }
     
     
     
     
     public String getItemDisplayName (ItemStack stack)
     {
          ToolMaterial mat = toolMaterial;
          
          String weapon = StatCollector.translateToLocal("tool." + toolName);
          String material = StatCollector.translateToLocal("material." + mat.getName());
          
          return material + " " + weapon;
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister register)
     {
          itemIcon = doRegister("tuxweapons", toolName + "_1", register);
          itemOverlay = doRegister("tuxweapons", toolName + "_2", register);
          
          overrideExists = Assets.resourceExists(new ResourceLocation("tuxweapons", "textures/items/override/" + toolMaterial.getName().toLowerCase() + "_" + toolName + ".png"));
          
          if (overrideExists)
          {
               overrideIcon = doRegister("/override/" + toolMaterial.getName().toLowerCase() + "_" + toolName, register);
          }
     }
     
     
     
     
     public boolean getIsRepairable (ItemStack repair, ItemStack gem)
     {
          if (OreDictionary.getOres(toolMaterial.getOreDictionaryName()) != null && !OreDictionary.getOres(toolMaterial.getOreDictionaryName()).isEmpty())
          {
               return OreDictionary.itemMatches(OreDictionary.getOres(toolMaterial.getOreDictionaryName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
          }
          
          return super.getIsRepairable(repair, gem);
     }
}
