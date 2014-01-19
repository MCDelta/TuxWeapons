package mcdelta.tuxweapons;

import static mcdelta.tuxweapons.damage.EnumDamageTypes.BASHER;
import static mcdelta.tuxweapons.damage.EnumDamageTypes.GOLDEN;
import static mcdelta.tuxweapons.damage.EnumDamageTypes.SLASHER;
import mcdelta.core.IContent;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.particle.EnumParticles;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.network.PacketHandler;
import mcdelta.tuxweapons.config.TWConfig;
import mcdelta.tuxweapons.damage.DamageModifier;
import mcdelta.tuxweapons.event.EventEnchants;
import mcdelta.tuxweapons.event.EventFOVModifier;
import mcdelta.tuxweapons.event.EventItemInfo;
import mcdelta.tuxweapons.handlers.PlayerTracker;
import mcdelta.tuxweapons.handlers.TickHandler;
import mcdelta.tuxweapons.network.PacketSpawnParticle;
import mcdelta.tuxweapons.network.PacketThrowablePickup;
import mcdelta.tuxweapons.proxy.TWCommonProxy;
import mcdelta.tuxweapons.support.TWSupportAM2;
import mcdelta.tuxweapons.support.TWSupportBOP;
import mcdelta.tuxweapons.support.TWSupportTE;
import mcdelta.tuxweapons.support.TWSupportThaumcraft;
import mcdelta.tuxweapons.support.TWSupportTwilightForest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod (modid = TuxWeapons.MOD_ID, useMetadata = true)
@NetworkMod (clientSideRequired = true, serverSideRequired = false, channels =
{ TuxWeapons.MOD_ID }, packetHandler = PacketHandler.class)
public class TuxWeapons extends ModDelta
{
     // TODO
     // - beheading enchant
     // - giant magma core drops
     // - death messages
     // - reimplement ukulele
     // - retexture magma core
     // - mod support
     
     public static final String  MOD_ID = "tuxweapons";
     
     @Instance (MOD_ID)
     public static TuxWeapons    instance;
     
     @SidedProxy (clientSide = "mcdelta.tuxweapons.proxy.TWClientProxy", serverSide = "mcdelta.tuxweapons.proxy.TWCommonProxy")
     public static TWCommonProxy proxy;
     
     @SideOnly (Side.CLIENT)
     public static CreativeTabs  tabTW;
     
     
     
     
     @Override
     public void deltaInit (final FMLPreInitializationEvent event)
     {
          this.init(event, new TWConfig());
     }
     
     
     
     
     @EventHandler
     public void preInit (final FMLPreInitializationEvent event)
     {
          PacketHandler.packets[2] = PacketSpawnParticle.class;
          PacketHandler.packets[3] = PacketThrowablePickup.class;
          
          doLimitedModSupport(new TWSupportThaumcraft());
          doLimitedModSupport(new TWSupportTwilightForest());
          doLimitedModSupport(new TWSupportBOP());
          doLimitedModSupport(new TWSupportTE());
          doLimitedModSupport(new TWSupportAM2());
     }
     
     
     
     
     @EventHandler
     public void load (final FMLInitializationEvent event)
     {
          MinecraftForge.EVENT_BUS.register(new EventEnchants());
          MinecraftForge.EVENT_BUS.register(new DamageModifier());
          MinecraftForge.EVENT_BUS.register(new EventItemInfo());
          MinecraftForge.EVENT_BUS.register(new EventFOVModifier());
          
          TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
          GameRegistry.registerPlayerTracker(new PlayerTracker());
          
          proxy.registerRenderers();
     }
     
     
     
     
     @EventHandler
     public void postInit (final FMLPostInitializationEvent event)
     {
          for (final Item item : Item.itemsList)
          {
               if (item instanceof ItemSword && !BASHER.effc_item.contains(item) && !SLASHER.effc_item.contains(item))
               {
                    SLASHER.effc_item.add(item);
               }
               
               else if (item instanceof ItemAxe && !BASHER.effc_item.contains(item) && !SLASHER.effc_item.contains(item))
               {
                    BASHER.effc_item.add(item);
               }
               
               if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().toLowerCase().contains("gold"))
               {
                    GOLDEN.effc_item.add(item);
               }
               
               if (item instanceof ItemAxe && ((ItemAxe) item).getToolMaterialName().toLowerCase().contains("gold"))
               {
                    GOLDEN.effc_item.add(item);
               }
          }
          
          TWContent.tab.iconStack = new ItemStack(TWContent.battleaxes.get(MaterialRegistry.DIAMOND));
     }
     
     
     
     
     public static void spawnParticle (final int particle, final World world, final double x, final double y, final double z, final Object... obj)
     {
          if (Assets.isClient())
          {
               EnumParticles.values()[particle].spawnParticle(world, x, y, z, obj);
          }
          
          else
          {
               PacketDispatcher.sendPacketToAllAround(x, y, z, 10, world.provider.dimensionId, Assets.populatePacket(new PacketSpawnParticle(particle, x, y, z)));
          }
     }
     
     private final IContent content = new TWContent();
     
     
     
     
     @Override
     public IContent content ()
     {
          return content;
     }
     
     
}
