package mcdelta.tuxweapons.item;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.Position;
import mcdelta.core.item.ItemDelta;
import mcdelta.core.item.ItemDeltaTool;
import mcdelta.core.logging.Logger;
import mcdelta.core.material.ItemMaterial;
import mcdelta.tuxweapons.TuxWeapons;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet53BlockChange;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWhock extends ItemDeltaTool
{
     public ItemWhock (final ItemMaterial mat)
     {
          super(TuxWeapons.instance, "whock", mat, ItemPickaxe.blocksEffectiveAgainst, 2.0F, false);
     }
     
     
     
     
     @Override
     public boolean onEntitySwing (final EntityLivingBase living, final ItemStack stack)
     {
          return false;
     }
     
     
     
     
     @Override
     public boolean onBlockDestroyed (final ItemStack stack, final World world, final int id, final int x, final int y, final int z, final EntityLivingBase living)
     {
          if (living instanceof EntityPlayer)
          {
               final MovingObjectPosition moving = getMovingObjectPositionFromPlayer(world, (EntityPlayer) living, false);
               final Position hitBlock = new Position(world, x, y, z);
               
               if (moving != null && moving.typeOfHit == EnumMovingObjectType.TILE)
               {
                    final List<Position> positions = getPositionsFromSide(hitBlock, moving.sideHit);
                    
                    for (final Position pos : positions)
                    {
                         if (!Assets.isAirBlock(pos))
                         {
                              if (living instanceof EntityPlayerMP)
                              {
                                   Block block = Block.blocksList[living.worldObj.getBlockId(pos.x, pos.y, pos.z)];
                                   
                                   if (block != null && MinecraftForge.getBlockHarvestLevel(block, world.getBlockMetadata(pos.x, pos.y, pos.z), "pickaxe") <= ((ItemWhock) stack.getItem()).itemMaterial.getHarvestLevel() && block.getBlockHardness(world, pos.x, pos.y, pos.z) >= 0)
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
     
     
     
     
     private boolean tryHarvestBlock (final World world, final EntityPlayerMP player, final int x, final int y, final int z)
     {
          final BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x, y, z);
          if (event.isCanceled())
          {
               return false;
          }
          final int l = world.getBlockId(x, y, z);
          final int i1 = world.getBlockMetadata(x, y, z);
          world.playAuxSFXAtEntity(player, 2001, x, y, z, l + (world.getBlockMetadata(x, y, z) << 12));
          boolean flag = false;
          
          if (player.theItemInWorldManager.isCreative())
          {
               flag = removeBlock(world, player, x, y, z);
               player.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(x, y, z, world));
          }
          else
          {
               final ItemStack itemstack = player.getCurrentEquippedItem();
               boolean flag1 = false;
               final Block block = Block.blocksList[l];
               if (block != null)
               {
                    flag1 = block.canHarvestBlock(player, i1);
               }
               
               Logger.debug(flag1, block);
               
               if (itemstack != null)
               {
                    if (itemstack.stackSize == 0)
                    {
                         player.destroyCurrentEquippedItem();
                    }
               }
               
               if (flag1)
               {
                    flag = removeBlock(world, player, x, y, z);
                    if (flag)
                    {
                         Block.blocksList[l].harvestBlock(world, player, x, y, z, i1);
                    }
               }
          }
          
          // Drop experience
          if (!player.theItemInWorldManager.isCreative() && flag && event != null)
          {
               Block.blocksList[l].dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
          }
          
          return flag;
     }
     
     
     
     
     private boolean removeBlock (final World world, final EntityPlayerMP player, final int x, final int y, final int z)
     {
          final Block block = Block.blocksList[world.getBlockId(x, y, z)];
          final int l = world.getBlockMetadata(x, y, z);
          
          if (block != null)
          {
               block.onBlockHarvested(world, x, y, z, l, player);
          }
          
          final boolean flag = block != null && block.removeBlockByPlayer(world, player, x, y, z);
          
          if (block != null && flag)
          {
               block.onBlockDestroyedByPlayer(world, x, y, z, l);
          }
          
          return flag;
     }
     
     
     
     
     private List<Position> getPositionsFromSide (final Position pos, final int side)
     {
          final List<Position> positions = new ArrayList<Position>();
          
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
     
     
     
     
     @Override
     public boolean canHarvestBlock (final Block block)
     {
          return block == Block.obsidian ? itemMaterial.getHarvestLevel() == 3 : block != Block.blockDiamond && block != Block.oreDiamond ? block != Block.oreEmerald && block != Block.blockEmerald ? block != Block.blockGold && block != Block.oreGold ? block != Block.blockIron && block != Block.oreIron ? block != Block.blockLapis && block != Block.oreLapis ? block != Block.oreRedstone && block != Block.oreRedstoneGlowing ? block.blockMaterial == Material.rock ? true : block.blockMaterial == Material.iron ? true : block.blockMaterial == Material.anvil : itemMaterial.getHarvestLevel() >= 2 : itemMaterial.getHarvestLevel() >= 1 : itemMaterial.getHarvestLevel() >= 1 : itemMaterial.getHarvestLevel() >= 2 : itemMaterial.getHarvestLevel() >= 2 : itemMaterial.getHarvestLevel() >= 2;
     }
     
     
     
     
     @Override
     public float getStrVsBlock (final ItemStack stack, final Block block, final int meta)
     {
          final float strength = super.getStrVsBlock(stack, block, meta);
          return strength / 4F;
     }
     
     
     
     
     @Override
     public void registerIcons (final IconRegister register)
     {
          itemIcon = ItemDelta.doRegister("tuxweapons", toolName + "_1", register);
          itemOverlay = ItemDelta.doRegister("tuxweapons", toolName + "_2", register);
          
          overrideExists = Assets.resourceExists(new ResourceLocation("tuxweapons", "textures/items/override/" + itemMaterial.name().toLowerCase() + "_" + toolName + ".png"));
          
          if (overrideExists)
          {
               overrideIcon =  ItemDelta.doRegister(mod.id(), "/override/" + itemMaterial.name().toLowerCase() + "_" + toolName, register);
          }
     }
     
     
     
     
     @Override
     public boolean getIsRepairable (final ItemStack repair, final ItemStack gem)
     {
          if (OreDictionary.getOres(itemMaterial.oreName()) != null && !OreDictionary.getOres(itemMaterial.oreName()).isEmpty())
          {
               return OreDictionary.itemMatches(OreDictionary.getOres(itemMaterial.oreName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
          }
          
          return super.getIsRepairable(repair, gem);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void getSubItems (final int id, final CreativeTabs tab, final List list)
     {
          final ItemStack stack = new ItemStack(id, 1, 0);
          if (itemMaterial.toolEnchant() != null)
          {
               stack.addEnchantment(itemMaterial.toolEnchant(), itemMaterial.toolEnchantLvl());
          }
          list.add(stack);
     }
}
