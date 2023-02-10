package me.allinkdev.skinfix.api.response;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseData {
    int expectedStatusCode() default 200;
}
