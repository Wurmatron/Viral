package wurmatron.viral.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import wurmatron.viral.common.reference.Global;

public class ServerConfig {

  ForgeConfigSpec.ConfigValue<Integer> radius;
  ForgeConfigSpec.ConfigValue<Double> passiveDamage;
  ForgeConfigSpec.ConfigValue<Boolean> hurtPassive;
  ForgeConfigSpec.ConfigValue<Boolean> infectPassive;
  ForgeConfigSpec.ConfigValue<Double> chance;
  ForgeConfigSpec.ConfigValue<Integer> particles;
  ForgeConfigSpec.ConfigValue<Integer> time;

  public ServerConfig(ForgeConfigSpec.Builder builder) {
    builder.push("general");
    radius = builder.comment("How close another infected mob has too be too infect another").translation(Global.MODID + ":" + "config." + ".radius")
        .define("radius", 8);
    passiveDamage = builder.comment("How much damage do passive mobs take").translation(Global.MODID + ":" + "config." + ".passiveDamage")
        .define("passiveDamage", 1.0);
    hurtPassive = builder.comment("Will the virus hurt passive mobs?").translation(Global.MODID + ":" + "config." + ".hurtPassive")
        .define("hurtPassive", true);
    infectPassive = builder.comment("Can the virus infected passive mobs?").translation(Global.MODID + ":" + "config." + ".infectPassive")
        .define("infectPassive", false);
    chance = builder.comment("Chance for the virus to spread").translation(Global.MODID + ":" + "config." + ".chance")
        .define("chance", 0.1);
    particles = builder.comment("0 = none, 1 = some, 2 = full").translation(Global.MODID + ":" + "config." + ".particles")
        .define("particles", 2);
    particles = builder.comment("icks between virus infection attempts").translation(Global.MODID + ":" + "config." + ".time")
        .define("time", 600);
    builder.pop();
  }
}
