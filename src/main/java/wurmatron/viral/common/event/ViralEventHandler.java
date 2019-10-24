package wurmatron.viral.common.event;


import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import wurmatron.viral.common.capabilities.IViral;
import wurmatron.viral.common.capabilities.ViralProvider;
import wurmatron.viral.common.config.ConfigHolder;

public class ViralEventHandler {

  private static int radius = ConfigHolder.SERVER_SPEC.get("radius");
  private Random rand = new Random();
  private int counter = 0;
  private float passiveDamage = ConfigHolder.SERVER_SPEC.get("passiveDamage");

  @SubscribeEvent
  public void onEntitySpawn(EntityJoinWorldEvent e) {
    if (!(e.getEntity() instanceof PlayerEntity) && e.getEntity() instanceof LivingEntity) {
      LivingEntity entity = (LivingEntity) e.getEntity();
      IViral status = entity.getCapability(ViralProvider.VIRAL, null).orElseGet(new NonNullSupplier<IViral>() {
        @Nonnull
        @Override
        public IViral get() {
          return null;
        }
      });
      if (getChancePercentage() >= 100) {
        status.set(1);
      } else if (rand.nextInt(getChancePercentage()) == 0) {
        status.set(1);
      }
    }
  }

  // TODO Imunity on dice roll
  @SubscribeEvent
  public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
    IViral status = e.getEntityLiving().getCapability(ViralProvider.VIRAL, null).orElseGet(new NonNullSupplier<IViral>() {
      @Nonnull
      @Override
      public IViral get() {
        return null;
      }
    });
    if (status.status() == 1) {
      if ((int) ConfigHolder.SERVER_SPEC.get("particles") > 0) {
        if ((int) ConfigHolder.SERVER_SPEC.get("particles") == 1 && counter == 10) {
          e.getEntityLiving()
              .world
              .addParticle(
                  ParticleTypes.DRAGON_BREATH,
                  e.getEntityLiving().posX,
                  e.getEntityLiving().posY,
                  e.getEntityLiving().posZ,
                  ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().getWidth()) / 5,
                  (rand.nextDouble() * (double) e.getEntityLiving().getHeight()) / 5,
                  ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().getWidth()) / 5);
          counter = 0;
        } else if ((int) ConfigHolder.SERVER_SPEC.get("particles") == 2) {
          e.getEntityLiving()
              .world
              .addParticle(
                  ParticleTypes.DRAGON_BREATH,
                  e.getEntityLiving().posX,
                  e.getEntityLiving().posY,
                  e.getEntityLiving().posZ,
                  ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().getWidth()) / 5,
                  (rand.nextDouble() * (double) e.getEntityLiving().getHeight()) / 5,
                  ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().getWidth()) / 5);
        } else if ((int) ConfigHolder.SERVER_SPEC.get("particles") != 0 && counter >= 0) {
          counter++;
        }
      }
      if (!e.getEntityLiving().world.isRemote
          && e.getEntityLiving().world.getDayTime() % (int) ConfigHolder.SERVER_SPEC.get("time") == 0) {
        spreadViral(e.getEntityLiving());
      }
      if (!(e.getEntityLiving() instanceof AnimalEntity)) {
        e.getEntityLiving()
            .getAttribute(SharedMonsterAttributes.MAX_HEALTH)
            .setBaseValue(
                e.getEntityLiving()
                    .getAttribute(SharedMonsterAttributes.MAX_HEALTH)
                    .getBaseValue()
                    * 2);
//        e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(5), 100));
//        e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(11), 100));
//        e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(1), 100, 2));
      } else if (e.getEntityLiving() instanceof AnimalEntity && (boolean) ConfigHolder.SERVER_SPEC.get("infectPassive")) {
//        e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(2), 100, 4));
        if ((boolean) ConfigHolder.SERVER_SPEC.get("hurtPassive")
            && ((AnimalEntity) e.getEntityLiving()).world.getDayTime() % 1000 == 0
            && passiveDamage > 0) {
          e.getEntityLiving().attackEntityFrom(DamageSource.MAGIC, passiveDamage);
        }
      } else {
        status.set(0);
      }
    }
  }

  private void spreadViral(LivingEntity entity) {
    List<Entity> area =
        entity.world.getEntitiesWithinAABBExcludingEntity(
            entity, entity.getBoundingBox().expand(radius, radius, radius));
    if (area.size() > 0) {
      for (Entity e : area) {
        if (!(e instanceof PlayerEntity) && e instanceof LivingEntity) {
          if (e instanceof AnimalEntity && !(boolean) ConfigHolder.SERVER_SPEC.get("infectPassive")) {
            return;
          }
          LivingEntity ent = (LivingEntity) e;
          if (!ent.world.isRemote && rand.nextInt(getChancePercentage() - 1) == 0) {
            IViral status = entity.getCapability(ViralProvider.VIRAL, null).orElseGet(new NonNullSupplier<IViral>() {
              @Nonnull
              @Override
              public IViral get() {
                return null;
              }
            });
            if (status.status() == 0) {
              status.set(1);
//              LogHandler(
//                  "Infected "
//                      + ent.getDisplayName().getUnformattedComponentText()
//                      + " X: "
//                      + ent.posX
//                      + " , Y: "
//                      + ent.posY
//                      + " , Z: "
//                      + ent.posZ);
            }
          }
        }
      }
    }
  }

  private int getChancePercentage() {
    return (int) ((double) ConfigHolder.SERVER_SPEC.get("chance") * 100);
  }
}
