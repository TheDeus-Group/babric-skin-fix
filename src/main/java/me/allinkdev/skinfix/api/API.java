package me.allinkdev.skinfix.api;

import me.allinkdev.skinfix.AbstractClass;
import me.allinkdev.skinfix.api.exception.APIException;
import me.allinkdev.skinfix.api.request.Request;
import me.allinkdev.skinfix.api.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class API extends AbstractClass {
    private static final Logger LOGGER = LoggerFactory.getLogger("SkinFix Mojang API Wrapper");

    public static <A extends Response, T extends Request<A>> A sendRequest(final T instance) throws APIException {
        final A response;
        LOGGER.info("Sending request: {}", instance.getClass().getTypeName());

        try {
            response = instance.send();
        } catch (IOException e) {
            throw new APIException(e);
        }

        return response;
    }
}
