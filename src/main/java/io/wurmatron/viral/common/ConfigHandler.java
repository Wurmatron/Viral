package io.wurmatron.viral.common;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {

    public static class Common {

        public final ForgeConfigSpec.ConfigValue<List<String>> blacklistedMobs;
        public final ForgeConfigSpec.ConfigValue<Integer> spreadChance;
        public final ForgeConfigSpec.ConfigValue<Integer> baseInfectionChance;
        public final ForgeConfigSpec.ConfigValue<Boolean> damagePassive;
        public final ForgeConfigSpec.ConfigValue<Float> passiveDamageAmount;
        public final ForgeConfigSpec.ConfigValue<Boolean> boostHostile;
        public final ForgeConfigSpec.ConfigValue<Boolean> neutralResist;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            this.blacklistedMobs = builder.comment("List of mobs that cannot get infected (Regex for mob class)").define("Mob Blacklist", new ArrayList<>());
            this.spreadChance = builder.comment("Change of this spreading (1/X)").define("Spread Chance", 10);
            this.baseInfectionChance = builder.comment("Change of a mob spawning infected (1/X)").define("Base Infection Chance", 40);
            this.damagePassive = builder.comment("Damage Passive Mobs").define("Damage Passive", true);
            this.passiveDamageAmount = builder.comment("Damage to Passive Mobs per spread (~5s)").define("Passive Damage", .5f);
            this.boostHostile = builder.comment("Boost Hostile Mobs Damage").define("Hostile Boost", true);
            this.neutralResist = builder.comment("Give Neutral Mobs Resistance").define("Neutral Resistance", true);
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
