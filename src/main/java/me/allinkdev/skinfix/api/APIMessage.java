package me.allinkdev.skinfix.api;

import java.lang.annotation.Annotation;

public class APIMessage {
    public static <T extends Annotation> T getAnnotation(final Class<? extends APIMessage> clazz, final Class<T> annotationClass) {
        return clazz.getDeclaredAnnotation(annotationClass);
    }

    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        return APIMessage.getAnnotation(this.getClass(), annotationClass);
    }
}
