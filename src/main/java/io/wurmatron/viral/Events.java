package io.wurmatron.viral;

import io.wurmatron.viral.common.ViralItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class Events {

    public static final String TAG_INFECTED = "infected";
    public static final String TAG_IMMUNE = "immune";

    @SubscribeEvent
    public static void LivingCreation(LivingSpawnEvent.SpecialSpawn e) {
        attemptToInfect(e.getEntityLiving(), false);
    }

    public static void attemptToInfect(LivingEntity entity, boolean spread) {
        for (String tag : entity.getTags())
            if (tag.equals(TAG_IMMUNE))
                return;
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
        Random rand = e.getEntity().level.getRandom();
        for (String tag : e.getEntityLiving().getTags()) {
            if (tag.equalsIgnoreCase("infected")) {
                LivingEntity liv = e.getEntityLiving();
                if (e.getEntity().level instanceof ServerWorld)
                    ((ServerWorld) e.getEntity().level)
                            .sendParticles(ParticleTypes.DRAGON_BREATH,
                                    liv.getX(),
                                    liv.getY() + (e.getEntityLiving().getBbHeight() / 2),
                                    liv.getZ(),
                                    1,
                                    ((rand.nextDouble() * .1) * (double) liv.getBbWidth()) / 5,
                                    (rand.nextDouble() * (double) liv.getBbHeight()) / 5,
                                    ((rand.nextDouble() * .1) * (double) liv.getBbWidth()) / 5,
                                    .1);
                if (liv.level.getGameTime() % 100 == 0) {
                    infectionTick(liv);
                }
            }
        }
    }

    public static void infectionTick(LivingEntity entity) {
        spreadInfection(entity);
        // TODO Config
        if (entity instanceof AnimalEntity) {
            entity.hurt(DamageSource.STARVE, .5f);
        } else if (entity instanceof MonsterEntity) {
            entity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 160));
        } else if (entity instanceof MobEntity) {
            entity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 160));
        }
    }

    public static void spreadInfection(LivingEntity entity) {
        AxisAlignedBB infectionArea = new AxisAlignedBB(entity.getX() - 8, entity.getY() - 8, entity.getZ() - 8, entity.getX() + 8, entity.getY() + 8, entity.getZ() + 8);
        for (Entity toBeInfected : entity.level.getEntities(null, infectionArea)) {
            if (toBeInfected instanceof LivingEntity)
                attemptToInfect((LivingEntity) toBeInfected, true);
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(AttackEntityEvent e) {
        if (e.getPlayer().inventory.getItem(e.getPlayer().inventory.selected).getItem().equals(ViralItems.SYRINGE_EMPTY.get())) {
            ItemStack hand = e.getPlayer().inventory.getItem(e.getPlayer().inventory.selected);
            for (String tag : e.getTarget().getTags()) {
                if (tag.equals(TAG_INFECTED)) {
                    hand.setCount(hand.getCount() - 1);
                    if (hand.getCount() <= 0)
                        hand = ItemStack.EMPTY;
                    e.getPlayer().inventory.setItem(e.getPlayer().inventory.selected, hand);
                    e.getPlayer().inventory.add(new ItemStack(ViralItems.SYRINGE_FILLED.get()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(PlayerInteractEvent.EntityInteract e) {
        if (e.getPlayer().inventory.getItem(e.getPlayer().inventory.selected).getItem().equals(ViralItems.SYRINGE_CURE.get())) {
            ItemStack hand = e.getPlayer().inventory.getItem(e.getPlayer().inventory.selected);
            for (String tag : e.getTarget().getTags()) {
                if (tag.equals(TAG_INFECTED)) {
                    hand.setCount(hand.getCount() - 1);
                    if (hand.getCount() <= 0)
                        hand = ItemStack.EMPTY;
                    e.getPlayer().inventory.setItem(e.getPlayer().inventory.selected, hand);
                    e.getTarget().removeTag(TAG_INFECTED);
                }
            }
        }
        if (e.getPlayer().inventory.getItem(e.getPlayer().inventory.selected).getItem().equals(ViralItems.SYRINGE_IMMUNITY.get())) {
            ItemStack hand = e.getPlayer().inventory.getItem(e.getPlayer().inventory.selected);
            hand.setCount(hand.getCount() - 1);
            if (hand.getCount() <= 0)
                hand = ItemStack.EMPTY;
            e.getPlayer().inventory.setItem(e.getPlayer().inventory.selected, hand);
            for (String tag : e.getTarget().getTags()) {
                if (tag.equals(TAG_INFECTED)) {
                    e.getTarget().removeTag(TAG_INFECTED);
                }
            }
            e.getTarget().addTag(TAG_IMMUNE);
        }
        if (e.getPlayer().inventory.getItem(e.getPlayer().inventory.selected).getItem().equals(ViralItems.SYRINGE_FILLED.get())) {
            ItemStack hand = e.getPlayer().inventory.getItem(e.getPlayer().inventory.selected);
            hand.setCount(hand.getCount() - 1);
            if (hand.getCount() <= 0)
                hand = ItemStack.EMPTY;
            e.getPlayer().inventory.setItem(e.getPlayer().inventory.selected, hand);
            e.getTarget().addTag(TAG_INFECTED);
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingSetAttackTargetEvent e) {
        if (e.getTarget() != null && e.getTarget() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) e.getTarget();
            if (player.getEffect(Viral.REPEL_EFFECT.get()) != null) {
                MobEntity mob = (MobEntity) e.getEntityLiving();
                mob.setTarget(null);
            }
        }
    }
}
