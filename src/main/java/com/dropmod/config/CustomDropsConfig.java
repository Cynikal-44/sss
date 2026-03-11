package com.dropmod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomDropsConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE = "custom_drops.json";
    private static CustomDropsConfig INSTANCE;

    // -----------------------------------------------------------------------
    // Zombie drops — replaces ALL vanilla zombie loot
    public List<DropEntry> zombieDrops = new ArrayList<>();

    // Skeleton drops — replaces ALL vanilla skeleton loot
    public List<DropEntry> skeletonDrops = new ArrayList<>();

    // -----------------------------------------------------------------------

    /** Build vanilla-matching defaults so the config is useful out of the box. */
    private static CustomDropsConfig defaults() {
        CustomDropsConfig cfg = new CustomDropsConfig();

        // Zombie: rotten flesh (2–5), occasionally iron ingot or carrot/potato
        cfg.zombieDrops.add(new DropEntry("minecraft:rotten_flesh", 2, 5, 1.0));
        cfg.zombieDrops.add(new DropEntry("minecraft:iron_ingot",   1, 1, 0.025));
        cfg.zombieDrops.add(new DropEntry("minecraft:carrot",       1, 1, 0.025));
        cfg.zombieDrops.add(new DropEntry("minecraft:potato",       1, 1, 0.025));

        // Skeleton: bones (0–2) and arrows (0–2)
        cfg.skeletonDrops.add(new DropEntry("minecraft:bone",  0, 2, 1.0));
        cfg.skeletonDrops.add(new DropEntry("minecraft:arrow", 0, 2, 1.0));

        return cfg;
    }

    // -----------------------------------------------------------------------

    public static CustomDropsConfig get() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    public static CustomDropsConfig load() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        File configFile = configDir.resolve(CONFIG_FILE).toFile();

        CustomDropsConfig config;
        if (configFile.exists()) {
            try (Reader reader = new FileReader(configFile)) {
                config = GSON.fromJson(reader, CustomDropsConfig.class);
                if (config == null) config = defaults();
                // Null-safe lists in case GSON didn't populate them
                if (config.zombieDrops   == null) config.zombieDrops   = new ArrayList<>();
                if (config.skeletonDrops == null) config.skeletonDrops = new ArrayList<>();
            } catch (IOException e) {
                System.err.println("[CustomDrops] Failed to read config, using defaults: " + e.getMessage());
                config = defaults();
            }
        } else {
            config = defaults();
        }

        config.save(configFile);
        INSTANCE = config;
        return config;
    }

    private void save(File file) {
        try {
            file.getParentFile().mkdirs();
            try (Writer writer = new FileWriter(file)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            System.err.println("[CustomDrops] Failed to write config: " + e.getMessage());
        }
    }
}
