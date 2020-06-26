package io.wurmatron.viral.common.event;

import static io.wurmatron.viral.common.references.Global.MODID;

import io.wurmatron.viral.api.ViralAPI;
import io.wurmatron.viral.api.capabilities.IViral;
import io.wurmatron.viral.api.capabilities.ViralProvider;
import io.wurmatron.viral.common.config.ConfigHolder;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ViralEvents {

  public static final ResourceLocation VIRAL = new ResourceLocation(MODID, "viral");
//  private static int radius = ConfigHolder.SERVER_SPEC.get("spreadRadius");

  @SubscribeEvent
  public void onCapabilities(AttachCapabilitiesEvent<Entity> e) {
    if (!(e.getObject() instanceof PlayerEntity)) { // Don't infect Players
      e.addCapability(VIRAL, new ViralProvider());
    }
  }

  @SubscribeEvent
  public void onLivingUpdate(LivingUpdateEvent e) {
    if (e.getEntityLiving() instanceof PlayerEntity) {
      return;
    }
    IViral viral = ViralAPI.getViral(e.getEntityLiving());
    if (viral != null && viral.status() == 1
        || viral != null && ConfigHolder.COMMON.debug.get()) {
      spawnParticles(e.getEntityLiving(), viral);
    }
  }

  private void spawnParticles(LivingEntity entity, IViral viral) {
    BlockPos pos = entity.getPosition();
    entity.world
        .addParticle(ParticleTypes.DRAGON_BREATH, pos.getX(), pos.getY(), pos.getZ(),
            ((entity.world.rand.nextDouble() - 0.5D) * (double) entity.getWidth()) / 5,
            (entity.world.rand.nextDouble() * (double) entity.getHeight()) / 5,
            ((entity.world.rand.nextDouble() - 0.5D) * (double) entity.getWidth()) / 5);
  }

  @SubscribeEvent
  public void onEntitySpawn(EntityJoinWorldEvent e) {
    if (!(e.getEntity() instanceof PlayerEntity) && e
        .getEntity() instanceof LivingEntity || e.getEntity() instanceof AnimalEntity && !ConfigHolder.COMMON.infectPassive.get()) {
      IViral viral = ViralAPI.getViral((LivingEntity) e.getEntity());
      if (viral != null) {
        if(!e.getWorld().isRemote) {
          viral.set(1);
        }
      }
    }
  }
}
