package io.wurmatron.viral.common.config;

import io.wurmatron.viral.common.references.Global;
import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {

  public ForgeConfigSpec.ConfigValue<Boolean> debug;
  public ForgeConfigSpec.ConfigValue<Integer> spreadRadius;
 public ForgeConfigSpec.ConfigValue<Integer> spreadChance;
 public ForgeConfigSpec.ConfigValue<Boolean> infectPassive;


  public ServerConfig(ForgeConfigSpec.Builder builder) {
    builder.push("general");
    debug = builder.comment("Enable debug mode").translation(
        Global.MODID + ":" + "config." + ".debug")
        .define("debug", false);
    spreadRadius = builder.comment("Range this will spread (in blocks)")
        .translation(Global.MODID + ":config.spreadRadius").define("spreadRadius", 8);
    spreadChance = builder.comment("Change a new entity will get infected ( 1 / x chance)")
        .translation(Global.MODID + ":config.spreadChance").define("spreadChance", 10);
    infectPassive = builder.comment("Can Viral spread to a passive mob")
        .translation(Global.MODID + ":config.infectPassive").define("infectPassive", true);
    builder.pop();
  }

}
