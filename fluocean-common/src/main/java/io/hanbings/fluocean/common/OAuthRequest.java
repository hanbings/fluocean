package io.hanbings.fluocean.common;

import io.hanbings.fluocean.common.interfaces.Request;
import io.hanbings.fluocean.common.interfaces.Response;
import io.hanbings.fluocean.common.interfaces.Serialization;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class OAuthRequest implements Request {
    OkHttpClient client;

    public OAuthRequest() {
        client = new OkHttpClient();
    }

    public OAuthRequest(Proxy proxy) {

    }

    @Override
    public <T, D, E> Response<T, D, E> get(D type, E error, Serialization serialization,
                                           @Nullable Proxy proxy, String url) {
        return null;
    }

    @Override
    public <T, D, E> Response<T, D, E> get(D type, E error, Serialization serialization,
                                           @Nullable Proxy proxy, String url, Map<String, String> params) {
        return null;
    }

    @Override
    public <T, D, E> Response<T, D, E> post(D type, E error, Serialization serialization,
                                            @Nullable Proxy proxy, String url) {
        return null;
    }

    @Override
    public <T, D, E> Response<T, D, E> post(D type, E error, Serialization serialization,
                                            @Nullable Proxy proxy, String url, Map<String, String> form) {
        return null;
    }
}
