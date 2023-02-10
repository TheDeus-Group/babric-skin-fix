package me.allinkdev.skinfix;

import lombok.Getter;
import lombok.Setter;
import me.allinkdev.skinfix.thread.SkinFixerThread;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SkinFix implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("babric-skin-fix");
    private static final int CORES = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(Math.max(CORES / 2, 1));
    @Getter
    @Setter
    private static WorldRenderer worldRenderer;

    public static void fixSkinAndCloak(final PlayerEntity player) {
        final String username = player.name;

        LOGGER.info("Fixing skin for {}!", username);

        final SkinFixerThread skinFixerThread = new SkinFixerThread(player, worldRenderer);
        EXECUTOR_SERVICE.submit(skinFixerThread);
    }

    @Override
    public void onInitialize() {

    }
}
