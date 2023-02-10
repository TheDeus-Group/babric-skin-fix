package me.allinkdev.skinfix.api.exception;

import java.io.IOException;

public class APIException extends IOException {
    public APIException(final IOException cause) {
        super("Exception while sending request!", cause);
    }

    public APIException(final String message) {
        super(message);
    }
}
