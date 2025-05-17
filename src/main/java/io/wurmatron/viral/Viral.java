package io.wurmatron.viral;

import io.wurmatron.viral.common.ConfigHandler;
import io.wurmatron.viral.common.ViralItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.wurmatron.viral.Viral.MODID;

@Mod(MODID)
public class Viral {

    public static final String MODID = "viral";

    public static final Logger LOGGER = LogManager.getLogger();

    public Viral() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::client);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.SPEC, "viral.toml");
        MinecraftForge.EVENT_BUS.register(Events.class);
        ViralItems.register(bus);
    }

    private void setup(FMLCommonSetupEvent e) {
    }

    private void client(FMLClientSetupEvent e) {

    }
}
