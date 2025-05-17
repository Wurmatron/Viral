package io.wurmatron.viral;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class Events {

    public static final String TAG_INFECTED = "infected";

    @SubscribeEvent
    public static void LivingCreation(LivingSpawnEvent.SpecialSpawn e) {
        attemptToInfect(e.getEntityLiving(), false);
    }

    public static void attemptToInfect(LivingEntity entity, boolean spread) {
        // TODO Config
        int rate = 10;
        if (spread)
            rate = 20;
        if (isAllowedToBeInfected(entity) && entity.getRandom().nextInt(rate) == 1) {
            entity.addTag(TAG_INFECTED);
        }
    }

    // TODO Config
    public static boolean isAllowedToBeInfected(LivingEntity e) {
        return !(e instanceof PlayerEntity);
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingUpdateEvent e) {
        Random rand = e.getEntity().getCommandSenderWorld().getRandom();
        for (String tag : e.getEntityLiving().getTags()) {
            if (tag.equalsIgnoreCase("infected")) {
                LivingEntity liv = e.getEntityLiving();
                ((ServerWorld) e.getEntityLiving()
                        .getCommandSenderWorld())
                        .sendParticles(ParticleTypes.DRAGON_BREATH,
                                liv.getX(),
                                liv.getY() + (e.getEntityLiving().getBbHeight() / 2),
                                liv.getZ(),
                                1,
                                ((rand.nextDouble() * .1) * (double) liv.getBbWidth()) / 5,
                                (rand.nextDouble() * (double) liv.getBbHeight()) / 5,
                                ((rand.nextDouble() * .1) * (double) liv.getBbWidth()) / 5,
                                .1);
                if (liv.getCommandSenderWorld().getGameTime() % 100 == 0) {
                    infectionTick(liv);
                }
            }
        }
    }

    public static void infectionTick(LivingEntity entity) {
        spreadInfection(entity);
        // TODO Do Infection Effect
    }

    public static void spreadInfection(LivingEntity entity) {
        AxisAlignedBB infectionArea = new AxisAlignedBB(entity.getX() - 8, entity.getY() - 8, entity.getZ() - 8, entity.getX() + 8, entity.getY() + 8, entity.getZ() + 8);
        for (Entity toBeInfected : entity.getCommandSenderWorld().getEntities(null, infectionArea)) {
            if (toBeInfected instanceof LivingEntity)
                attemptToInfect((LivingEntity) toBeInfected, true);
        }
    }
}
