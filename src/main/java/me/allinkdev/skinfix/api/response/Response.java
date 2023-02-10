package me.allinkdev.skinfix.api.response;

import me.allinkdev.skinfix.api.APIMessage;

public abstract class Response extends APIMessage {
    public ResponseData getMetadata() {
        return getAnnotation(ResponseData.class);
    }
}
