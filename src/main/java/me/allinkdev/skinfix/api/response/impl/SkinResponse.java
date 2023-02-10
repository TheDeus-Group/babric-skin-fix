package me.allinkdev.skinfix.api.response.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.allinkdev.skinfix.api.response.Response;
import me.allinkdev.skinfix.api.response.ResponseData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;

import java.io.StringReader;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

@ResponseData
@EqualsAndHashCode(callSuper = true)
@Data
public class SkinResponse extends Response {
    private static final Base64.Decoder base64Decoder = Base64.getDecoder();
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private final String id;
    private final String name;
    private final Set<Property> properties;
    private final boolean legacy;

    public Optional<TextureProperty> getTexturesProperty() {
        final Optional<Property> rawTexturePropertyOptional = properties.stream()
                .filter(p -> p.name.equals("textures"))
                .findFirst();

        if (!rawTexturePropertyOptional.isPresent()) {
            return Optional.empty();
        }

        final Property rawTextureProperty = rawTexturePropertyOptional.get();
        final String rawTexturePropertyValueEncoded = rawTextureProperty.value;
        final String rawTexturePropertyValue = new String(base64Decoder.decode(rawTexturePropertyValueEncoded));
        final StringReader reader = new StringReader(rawTexturePropertyValue);

        final TextureProperty textureProperty = GSON.fromJson(reader, TextureProperty.class);
        return Optional.of(textureProperty);
    }

    @Data
    public static class Property {
        private final String name;
        private final String value;
        @Nullable
        private final String signature;
    }

    @Data
    public static class TextureProperty {
        private final long timestamp;
        private final String profileId;
        private final String profileName;
        private final boolean signatureRequired;
        private final Textures textures;

        // SonarLint complains about SKIN & CAPE not being camel-case, however their inconsistent names when compared to the rest of the response was Mojang's choice. I'd change it if I could.
        @SuppressWarnings("java:S116")
        @Data
        public static class Textures {
            @Nullable
            private final Texture SKIN;
            @Nullable
            private final Texture CAPE;

            @Data
            public static class Texture {
                private final String url;
                @Nullable
                private final TextureMetadata metadata;

                @Data
                public static class TextureMetadata {
                    private final String model;
                }
            }
        }
    }
}
