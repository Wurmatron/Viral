package wurmatron.viral.common.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.common.refrence.Global;
import wurmatron.viral.common.utils.LogHandler;

import java.io.File;

public class ConfigHandler {

		public static File location;
		public static Configuration config;

		public static Property debug;

		public static void preInit (FMLPreInitializationEvent e) {
				location = e.getSuggestedConfigurationFile();
				config = new Configuration(location);
				loadConfig();
		}

		public static void loadConfig () {
				if (config != null) {
						LogHandler.info("Loading config");
						debug = config.get(Configuration.CATEGORY_GENERAL, "debug", Defaults.DEBUG, "Enable Debug Mode");
						Settings.debug = debug.getBoolean();
						if (config.hasChanged()) {
								LogHandler.info("Config saved");
								config.save();
						}
				}
		}

		@SubscribeEvent
		public void configChanged (ConfigChangedEvent.OnConfigChangedEvent e) {
				if (e.getModID().equals(Global.MODID))
						loadConfig();
		}
}
