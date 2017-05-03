package wurmatron.viral.common.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.common.reference.Global;
import wurmatron.viral.common.utils.LogHandler;

import java.io.File;

public class ConfigHandler {

	public static File location;
	public static Configuration config;
	public static Property hurtPassive;
	public static Property passiveDamage;
	public static Property torches;
	public static Property radius;
	private static Property debug;
	private static Property chance;
	private static Property time;
	private static Property range;
	private static Property particles;
	private static Property recipes;
	private static Property infectPassive;

	public static void preInit (FMLPreInitializationEvent e) {
		location = e.getSuggestedConfigurationFile ();
		config = new Configuration (location);
		loadConfig ();
	}

	public static void loadConfig () {
		LogHandler.info ("Loading config");
		debug = config.get (Configuration.CATEGORY_GENERAL,"debug",Defaults.DEBUG,"Enable Debug Mode");
		Settings.debug = debug.getBoolean ();
		chance = config.get (Configuration.CATEGORY_GENERAL,"chance",Defaults.CHANCE,"Chance for the virus to spread",0.001,1);
		Settings.chance = chance.getDouble ();
		time = config.get (Configuration.CATEGORY_GENERAL,"time",Defaults.TIME,"Ticks between virus infection attempts",2,Integer.MAX_VALUE);
		Settings.time = time.getInt ();
		range = config.get (Configuration.CATEGORY_GENERAL,"range",Defaults.RANGE,"How close another infected mob has too be too infect another",0,64);
		Settings.range = range.getInt ();
		particles = config.get (Configuration.CATEGORY_GENERAL,"particles",Defaults.particles,"0 = none, 1 = some, 2 = full",0,2);
		Settings.particles = particles.getInt ();
		recipes = config.get (Configuration.CATEGORY_GENERAL,"recipes",Defaults.recipes,"Are this mods recipes enabled");
		Settings.recipes = recipes.getBoolean ();
		hurtPassive = config.get (Configuration.CATEGORY_GENERAL,"hurtPassive",Defaults.hurtPassive,"Will the virus hurt passive mobs?");
		Settings.hurtPassive = hurtPassive.getBoolean ();
		infectPassive = config.get (Configuration.CATEGORY_GENERAL,"infectPassive",Defaults.infectPassive,"Can the virus infected passive mobs?");
		Settings.infectPassive = infectPassive.getBoolean ();
		passiveDamage = config.get (Configuration.CATEGORY_GENERAL,"passiveDamage",Defaults.passiveDamage,"How much damage do passive mobs take");
		Settings.passiveDamage = passiveDamage.getDouble ();
		torches = config.get (Configuration.CATEGORY_GENERAL,"torches",Defaults.torches,"Are the interdiction torches enabled");
		Settings.torches = torches.getBoolean ();
		radius = config.get (Configuration.CATEGORY_GENERAL,"radius",Defaults.radius,"Range of the interdiction torches",0,64);
		Settings.radius = radius.getInt ();
		if (config.hasChanged ()) {
			LogHandler.info ("Config saved");
			config.save ();
		}
	}

	@SubscribeEvent
	public void configChanged (ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID ().equals (Global.MODID))
			loadConfig ();
	}
}
