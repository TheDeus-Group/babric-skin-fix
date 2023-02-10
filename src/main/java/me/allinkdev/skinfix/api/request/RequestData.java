package me.allinkdev.skinfix.api.request;

import me.allinkdev.skinfix.api.response.Response;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestData {
    String url();

    Class<? extends Response> responseClass();
}
