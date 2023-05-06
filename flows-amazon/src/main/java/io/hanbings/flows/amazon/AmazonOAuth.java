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

package io.hanbings.flows.amazon;

import io.hanbings.flows.common.OAuth;

import java.util.List;
import java.util.Map;

public class AmazonOAuth extends OAuth<AmazonAccess, AmazonAccess.Wrong> {
    private AmazonOAuth() {
        super(null, null, null, null);
    }

    public AmazonOAuth(String client, String secret, String redirect) {
        super(
                "https://www.amazon.com/ap/oa",
                "https://api.amazon.com/auth/o2/token",
                List.of(),
                Map.of()
        );

        this.client(client);
        this.secret(secret);
        this.redirect(redirect);
    }

    public AmazonOAuth(String client, String secret, String redirect,
                       List<String> scopes, Map<String, String> params) {
        super(
                "https://www.amazon.com/ap/oa",
                "https://api.amazon.com/auth/o2/token",
                scopes,
                params
        );

        this.client(client);
        this.secret(secret);
        this.redirect(redirect);
    }
}
