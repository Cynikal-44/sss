package com.dropmod;

import com.dropmod.config.CustomDropsConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomDropsMod implements ModInitializer {

    public static final String MOD_ID = "customdrops";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CustomDropsConfig cfg = CustomDropsConfig.load();
        LOGGER.info("[CustomDrops] Loaded — {} zombie drop(s), {} skeleton drop(s)",
                cfg.zombieDrops.size(), cfg.skeletonDrops.size());
    }
}
