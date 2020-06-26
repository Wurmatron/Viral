package io.wurmatron.viral.common.config;

import io.wurmatron.viral.client.config.ClientConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHolder {

  public static final ForgeConfigSpec CLIENT_SPEC;
  public static final ForgeConfigSpec COMMON_SPEC;
  public static final ClientConfig CLIENT;
  public static final ServerConfig COMMON;

  static {
    // Client
    Pair<ClientConfig, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
    CLIENT = clientPair.getLeft();
    CLIENT_SPEC = clientPair.getRight();
    // Server
    final Pair<ServerConfig, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
    COMMON = commonPair.getLeft();
    COMMON_SPEC = commonPair.getRight();
  }
}
