package io.wurmatron.viral;

import io.wurmatron.viral.api.capabilities.IViral;
import io.wurmatron.viral.api.capabilities.ViralFactory;
import io.wurmatron.viral.api.capabilities.ViralStorage;
import io.wurmatron.viral.common.config.ConfigHolder;
import io.wurmatron.viral.common.event.ViralEvents;
import io.wurmatron.viral.common.references.Global;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = Global.MODID)
public class Viral {

  public static final Logger LOGGER = LogManager.getLogger();
  public static CheckResult versionStatus;

  public Viral() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    // Config
    ModLoadingContext loadContext = ModLoadingContext.get();
    loadContext.registerConfig(Type.CLIENT, ConfigHolder.CLIENT_SPEC);
    loadContext.registerConfig(Type.COMMON, ConfigHolder.COMMON_SPEC);
  }

  private void setup(FMLCommonSetupEvent event) {
    versionStatus = VersionChecker
        .getResult(ModLoadingContext.get().getActiveContainer().getModInfo());
    CapabilityManager.INSTANCE
        .register(IViral.class, new ViralStorage(), new ViralFactory());
    MinecraftForge.EVENT_BUS.register(new ViralEvents());
  }

  private void doClientStuff(FMLClientSetupEvent event) {

  }
}
