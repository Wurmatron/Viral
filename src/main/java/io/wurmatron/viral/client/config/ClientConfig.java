package io.wurmatron.viral.client.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

  public ClientConfig(ForgeConfigSpec.Builder builder) {
    builder.push("general");

    builder.pop();
  }
}
