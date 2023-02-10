package me.allinkdev.skinfix.api.request;

import me.allinkdev.skinfix.api.APIMessage;
import me.allinkdev.skinfix.api.exception.APIException;
import me.allinkdev.skinfix.api.response.Response;
import me.allinkdev.skinfix.api.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.include.com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Request<T extends Response> extends APIMessage {
    protected static final Gson GSON = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger("Generic Request");

    protected RequestData getMetadata() {
        return getAnnotation(RequestData.class);
    }

    public abstract T send() throws IOException;

    @SuppressWarnings("unchecked")
    protected T send(final String stringUrl) throws IOException {
        final URL url = new URL(stringUrl);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        final RequestData metadata = getMetadata();
        final Class<? extends Response> responseClass = metadata.responseClass();

        connection.connect();

        final ResponseData responseMetadata = APIMessage.getAnnotation(responseClass, ResponseData.class);
        final int expectedStatusCode = responseMetadata.expectedStatusCode();
        final int actualStatusCode = connection.getResponseCode();

        if (expectedStatusCode != actualStatusCode) {
            final String message = "Received the status code" + actualStatusCode + " while we were expecting " + expectedStatusCode;
            LOGGER.warn(message);
            throw new APIException(message);
        }

        final InputStream responseStream = connection.getInputStream();
        final InputStreamReader reader = new InputStreamReader(responseStream);


        return (T) GSON.fromJson(reader, responseClass);
    }
}
