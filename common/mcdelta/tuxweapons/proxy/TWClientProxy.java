package mcdelta.tuxweapons.proxy;

import mcdelta.core.assets.client.RenderAssets;
import mcdelta.core.client.entity.RenderThrownItem;
import mcdelta.tuxweapons.client.entity.RenderBolt;
import mcdelta.tuxweapons.client.entity.RenderDynamite;
import mcdelta.tuxweapons.client.entity.RenderGrappHook;
import mcdelta.tuxweapons.client.entity.RenderKnife;
import mcdelta.tuxweapons.client.entity.RenderSpear;
import mcdelta.tuxweapons.client.item.RenderCrossbow;
import mcdelta.tuxweapons.client.item.RenderFireChargeCannon;
import mcdelta.tuxweapons.client.item.RenderItemPotion;
import mcdelta.tuxweapons.entity.EntityBolt;
import mcdelta.tuxweapons.entity.EntityDynamite;
import mcdelta.tuxweapons.entity.EntityEMPGrenade;
import mcdelta.tuxweapons.entity.EntityGrappHook;
import mcdelta.tuxweapons.entity.EntityKnife;
import mcdelta.tuxweapons.entity.EntitySpear;
import mcdelta.tuxweapons.entity.EntityTWFireball;
import mcdelta.tuxweapons.item.ItemTW;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class TWClientProxy extends TWCommonProxy
{
     @Override
     public void registerRenderers ()
     {
          RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderSpear());
          RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new RenderKnife());
          RenderingRegistry.registerEntityRenderingHandler(EntityGrappHook.class, new RenderGrappHook());
          RenderingRegistry.registerEntityRenderingHandler(EntityTWFireball.class, new RenderThrownItem(Item.fireballCharge));
          RenderingRegistry.registerEntityRenderingHandler(EntityBolt.class, new RenderBolt());
          RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new RenderDynamite());
          RenderingRegistry.registerEntityRenderingHandler(EntityEMPGrenade.class, new RenderThrownItem(ItemTW.empGrenade));
          
          MinecraftForgeClient.registerItemRenderer(ItemTW.fireChargeCannon.itemID, new RenderFireChargeCannon());
          MinecraftForgeClient.registerItemRenderer(ItemTW.crossBow.itemID, new RenderCrossbow());
          MinecraftForgeClient.registerItemRenderer(Item.potion.itemID, new RenderItemPotion());
          
          RenderAssets.flipInInventory.addAll(ItemTW.spears.values());
     }
}
