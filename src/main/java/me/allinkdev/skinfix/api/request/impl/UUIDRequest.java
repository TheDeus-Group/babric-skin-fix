package me.allinkdev.skinfix.api.request.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.allinkdev.skinfix.api.request.Request;
import me.allinkdev.skinfix.api.request.RequestData;
import me.allinkdev.skinfix.api.response.impl.UUIDResponse;

import java.io.IOException;

@RequestData(url = "https://api.mojang.com/users/profiles/minecraft/%s", responseClass = UUIDResponse.class)
@EqualsAndHashCode(callSuper = true)
@Data
public class UUIDRequest extends Request<UUIDResponse> {
    private final String username;

    @Override
    public UUIDResponse send() throws IOException {
        final RequestData metadata = getMetadata();
        final String url = metadata.url();
        final String formatted = String.format(url, username);

        return send(formatted);
    }
}
