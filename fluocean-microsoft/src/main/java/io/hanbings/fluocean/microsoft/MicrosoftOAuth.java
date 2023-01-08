package io.hanbings.fluocean.microsoft;

import io.hanbings.fluocean.common.OAuth;
import io.hanbings.fluocean.common.OAuthCallback;
import io.hanbings.fluocean.common.interfaces.Callback;
import io.hanbings.fluocean.common.interfaces.Refreshable;
import io.hanbings.fluocean.common.interfaces.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class MicrosoftOAuth
        extends
        OAuth<MicrosoftAccess, MicrosoftAccess.Wrong>
        implements
        Refreshable<MicrosoftAccess, MicrosoftAccess.Wrong> {
    private MicrosoftOAuth() {
        super(
                "https://login.microsoftonline.com/common/oauth2/v2.0/authorize",
                "https://login.microsoftonline.com/common/oauth2/v2.0/token"
        );
    }

    public MicrosoftOAuth(String client, String secret, String redirect) {
        super(
                "https://login.microsoftonline.com/common/oauth2/v2.0/authorize",
                "https://login.microsoftonline.com/common/oauth2/v2.0/token"
        );

        this.client(client);
        this.secret(secret);
        this.redirect(redirect);
    }

    public MicrosoftOAuth(String authorization, String access, String client, String secret, String redirect) {
        super(authorization, access);

        this.client(client);
        this.secret(secret);
        this.redirect(redirect);
    }

    @Override
    public String authorize(List<String> scopes, Map<String, String> params) {
        return super.authorize(
                scopes.size() > 0 ? scopes : List.of("openid"),
                new HashMap<>() {{
                    putAll(params);
                    put("response_type", "code");
                }}
        );
    }

    @Override
    public Callback<MicrosoftAccess, MicrosoftAccess.Wrong> token(String code, String state, String redirect) {
        Response response = this.request()
                .get()
                .post(
                        this.serialization().get(),
                        this.proxy() == null ? null : this.proxy().get(),
                        this.access(),
                        Map.of(
                                "client_id", this.client(),
                                "client_secret", this.secret(),
                                "grant_type", "authorization_code",
                                "redirect_uri", redirect,
                                "code", code
                        ),
                        Map.of(
                                "Accept", "application/json",
                                "Content-Type", "application/x-www-form-urlencoded"
                        )
                );

        if (response.code() == 200) {
            MicrosoftAccess access = this.serialization()
                    .get()
                    .object(MicrosoftAccess.class, response.raw());

            return OAuthCallback.response(access.accessToken(), access, null, response);

        }

        if (response.code() == 400) {
            MicrosoftAccess.Wrong wrong = this.serialization()
                    .get()
                    .object(MicrosoftAccess.Wrong.class, response.raw());

            return OAuthCallback.response(null, null, wrong, response);
        }

        return OAuthCallback.exception(
                null,
                response.exception() ? response.throwable() : new IllegalArgumentException()
        );
    }

    @Override
    public Callback<MicrosoftAccess, MicrosoftAccess.Wrong> refresh(String token) {
        Response response = this.request()
                .get()
                .post(
                        this.serialization().get(),
                        this.proxy() == null ? null : this.proxy().get(),
                        this.access(),
                        Map.of(
                                "client_id", this.client(),
                                "client_secret", this.secret(),
                                "grant_type", "refresh_token",
                                "refresh_token", token
                        ),
                        Map.of(
                                "Accept", "application/json",
                                "Content-Type", "application/x-www-form-urlencoded"
                        )
                );

        if (response.code() == 200) {
            MicrosoftAccess access = this.serialization()
                    .get()
                    .object(MicrosoftAccess.class, response.raw());

            return OAuthCallback.response(access.accessToken(), access, null, response);

        }

        if (response.code() == 400) {
            MicrosoftAccess.Wrong wrong = this.serialization()
                    .get()
                    .object(MicrosoftAccess.Wrong.class, response.raw());

            return OAuthCallback.response(null, null, wrong, response);
        }

        return OAuthCallback.exception(
                null,
                response.exception() ? response.throwable() : new IllegalArgumentException()
        );
    }
}
