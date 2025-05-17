package io.wurmatron.viral.common;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {

    public static class Common {

        public final ForgeConfigSpec.ConfigValue<List<String>> blacklistedMobs;
        public static  List<String> defaultMobBlacklist = new ArrayList<>();

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            this.blacklistedMobs = builder.comment("List of mobs that cannot get infected").define("Mob Blacklist", defaultMobBlacklist);
            builder.pop();
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec SPEC;

    static {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = commonSpecPair.getLeft();
        SPEC = commonSpecPair.getRight();
    }
}
