package dev.compactmods.machines.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {
    public static final ModConfigSpec CONFIG;

    public static final ModConfigSpec.BooleanValue DISABLE_DEPTH_CHECKS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        DISABLE_DEPTH_CHECKS = builder
                .comment("If true, players will not be prevented from entering rooms deeper than their max depth")
                .define("disableDepthChecks", true);

        CONFIG = builder.build();
    }
}
