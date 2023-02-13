package me.allinkdev.skinfix.thread;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.allinkdev.skinfix.api.API;
import me.allinkdev.skinfix.api.exception.APIException;
import me.allinkdev.skinfix.api.request.impl.SkinRequest;
import me.allinkdev.skinfix.api.request.impl.UUIDRequest;
import me.allinkdev.skinfix.api.response.impl.SkinResponse;
import me.allinkdev.skinfix.api.response.impl.UUIDResponse;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
public class SkinFixerThread extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger("Skin Fixer Thread");
    private final PlayerEntity target;
    private final WorldRenderer worldRenderer;

    @Override
    public void run() {
        final String username;

        while (target.name == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LOGGER.error("Got interrupted while we were waiting for player username to become defined!", e);
                this.interrupt();
            }
        }

        username = target.name;
        LOGGER.info("Fixing skin of {}!", username);
        final UUIDRequest request = new UUIDRequest(username);

        final UUIDResponse uuidResponse;

        LOGGER.info("Requesting {}'s UUID...", username);
        try {
            uuidResponse = API.sendRequest(request);
            LOGGER.info("Received {}'s UUID!", username);
        } catch (APIException e) {
            LOGGER.error("Failed to get UUID of {} in order to fix their cloak, interrupting thread!", username, e);
            return;
        }

        final String id = uuidResponse.getId();
        final SkinRequest skinRequest = new SkinRequest(id);

        final SkinResponse skinResponse;

        LOGGER.info("Requesting {}'s skin...", username);
        try {
            skinResponse = API.sendRequest(skinRequest);
            LOGGER.info("Received {}'s skin!", username);
        } catch (APIException e) {
            LOGGER.error("Failed to get skin of {} in order to fix their cloak, interrupting thread!", username, e);
            return;
        }

        final Optional<SkinResponse.TextureProperty> texturesOptional = skinResponse.getTexturesProperty();

        if (!texturesOptional.isPresent()) {
            LOGGER.info("{} somehow does not have textures, skipping!", username);
            return;
        }

        final SkinResponse.TextureProperty textureProperty = texturesOptional.get();
        final SkinResponse.TextureProperty.Textures textures = textureProperty.getTextures();
        final SkinResponse.TextureProperty.Textures.Texture skin = textures.getSKIN();

        if (skin == null) {
            LOGGER.info("{} does not have a skin, skipping!", username);
        } else {
            target.skinUrl = skin.getUrl();
        }

        final SkinResponse.TextureProperty.Textures.Texture cape = textures.getCAPE();

        if (cape == null) {
            LOGGER.info("{} does not have a cape, skipping!", username);
        } else {
            final String capeUrl = cape.getUrl();

            target.playerCapeUrl = capeUrl;
            target.capeUrl = capeUrl;
        }


        LOGGER.info("Successfully fixed skin of {}!", username);

        worldRenderer.loadEntitySkin(target);
    }
}
