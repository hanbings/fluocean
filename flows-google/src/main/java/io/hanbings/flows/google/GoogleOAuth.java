/*
 * Copyright 2023 Flows
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.hanbings.flows.google;

import io.hanbings.flows.common.OAuth;
import io.hanbings.flows.common.OAuthCallback;
import io.hanbings.flows.common.interfaces.Callback;
import io.hanbings.flows.common.interfaces.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class GoogleOAuth extends OAuth<GoogleAccess, GoogleAccess.Wrong> {
    private GoogleOAuth() {
        super(null, null, null, null);
    }

    public GoogleOAuth(String client, String secret, String redirect) {
        super(
                "https://accounts.google.com/o/oauth2/v2/auth",
                "https://oauth2.googleapis.com/token",
                List.of(),
                Map.of()
        );

        this.client(client);
        this.secret(secret);
        this.redirect(redirect);
    }

    public GoogleOAuth(String client, String secret, String redirect,
                       List<String> scopes, Map<String, String> params) {
        super(
                "https://accounts.google.com/o/oauth2/v2/auth",
                "https://oauth2.googleapis.com/token",
                scopes,
                params
        );

        this.client(client);
        this.secret(secret);
        this.redirect(redirect);
    }

    @Override
    public String authorize(List<String> scopes, Map<String, String> params) {
        return super.authorize(
                scopes == null ? List.of("openid") : scopes,
                new HashMap<>() {{
                    putAll(params);
                    put("response_type", "code");
                }}
        );
    }

    @Override
    public Callback<GoogleAccess, GoogleAccess.Wrong> token(String code, String state, String redirect) {
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
            GoogleAccess access = this.serialization()
                    .get()
                    .object(GoogleAccess.class, response.raw());

            return OAuthCallback.response(access.accessToken(), access, null, response);

        }

        if (response.code() == 400) {
            GoogleAccess.Wrong wrong = this.serialization()
                    .get()
                    .object(GoogleAccess.Wrong.class, response.raw());

            return OAuthCallback.response(null, null, wrong, response);
        }

        return OAuthCallback.exception(
                null,
                response.exception() ? response.throwable() : new IllegalArgumentException()
        );
    }
}
