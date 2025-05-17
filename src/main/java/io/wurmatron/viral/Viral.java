package io.wurmatron.viral;

import io.wurmatron.viral.common.ConfigHandler;
import io.wurmatron.viral.common.RepelEffect;
import io.wurmatron.viral.common.ViralItems;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.wurmatron.viral.Viral.MODID;

@Mod(MODID)
public class Viral {

    public static final String MODID = "viral";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Viral.MODID);
    public static final RegistryObject<Effect> REPEL_EFFECT = EFFECTS.register("repel", () -> new RepelEffect(EffectType.BENEFICIAL, 0xc98fff));

    public Viral() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::client);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.SPEC, "viral.toml");
        MinecraftForge.EVENT_BUS.register(Events.class);
        ViralItems.register(bus);
        EFFECTS.register(bus);
    }

    private void setup(FMLCommonSetupEvent e) {
    }

    private void client(FMLClientSetupEvent e) {

    }
}
