package io.wurmatron.viral;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class Events {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Viral.MODID);
    public static final RegistryObject<Attribute> INFECTED = ATTRIBUTES.register("attack_reach", () -> (Attribute) new RangedAttribute("attribute.viral.infected", 1D, 0.0D, 64D).setSyncable(true));

    public static AttributeModifierMap.MutableAttribute createInfected() {
        return LivingEntity.createLivingAttributes().add(INFECTED.get(), 1d);
    }

    @SubscribeEvent
    public static void newEntityAttributes(LivingSpawnEvent e) {
//        if (e.getEntityLiving() instanceof PigEntity) {
//            PigEntity pig = (PigEntity) e.getEntityLiving();
//            pig.addTag("infected");
//        }
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

            }
        }
    }
}
