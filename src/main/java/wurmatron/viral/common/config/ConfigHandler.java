package wurmatron.viral.common.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.common.reference.Global;

@Config(modid = Global.MODID)
public class ConfigHandler {

    @Config.Comment("Will the virus hurt passive mobs?")
    public static boolean hurtPassive = Defaults.DEBUG;

    @Config.Comment("How much damage do passive mobs take")
    public static double passiveDamage = Defaults.PASSIVE_DAMAGE;

    @Config.Comment("Are the interdiction TORCHES enabled")
    public static boolean torches = Defaults.TORCHES;

    @Config.Comment("How close another infected mob has too be too infect another")
    public static int radius = Defaults.RANGE;

    @Config.Comment("Enable Debug Mode")
    public static boolean debug = Defaults.DEBUG;

    @Config.Comment("Chance for the virus to spread")
    public static double chance = Defaults.CHANCE;

    @Config.Comment("Ticks between virus infection attempts")
    public static int time = Defaults.TIME;

    @Config.Comment("Range of the interdiction TORCHES")
    public static int range = Defaults.RANGE;

    @Config.Comment("0 = none, 1 = some, 2 = full")
    public static int particles = Defaults.PARTICLES;

    @Config.Comment("Can the virus infected passive mobs?")
    public static boolean infectPassive = Defaults.infectPassive;

    @Config.Comment("Chance to collected the virus")
    public static int chanceToCollect = 60;

    @Config.Comment("How much more HP aggressive mobs can gain when infected (2 = 200% so 2x hp)")
    public static double maxHPGrowth = 2;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Global.MODID))
            ConfigManager.load(Global.MODID, Config.Type.INSTANCE);
    }
}
