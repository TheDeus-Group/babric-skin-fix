package me.allinkdev.skinfix.api.request.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.allinkdev.skinfix.api.request.Request;
import me.allinkdev.skinfix.api.request.RequestData;
import me.allinkdev.skinfix.api.response.impl.SkinResponse;

import java.io.IOException;

@RequestData(url = "https://sessionserver.mojang.com/session/minecraft/profile/%s", responseClass = SkinResponse.class)
@EqualsAndHashCode(callSuper = true)
@Data
public class SkinRequest extends Request<SkinResponse> {
    private final String id;

    @Override
    public SkinResponse send() throws IOException {
        final RequestData metadata = getMetadata();
        final String url = metadata.url();
        final String formatted = String.format(url, id);

        return send(formatted);
    }
}
