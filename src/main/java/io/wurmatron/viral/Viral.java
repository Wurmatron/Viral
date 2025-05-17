package io.wurmatron.viral;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.wurmatron.viral.Viral.MODID;

@Mod(MODID)
public class Viral {

    public static final String MODID = "viral";

    private static final Logger LOGGER = LogManager.getLogger();

    public Viral() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::client);
        MinecraftForge.EVENT_BUS.register(Events.class);
        Events.ATTRIBUTES.register(bus);
    }

    private void setup(FMLCommonSetupEvent e) {
//    CapabilityManager.INSTANCE.register(IViral.class, new ViralStorage(), ViralData::new);
    }

    private void client(FMLClientSetupEvent e) {

    }
}
