/*
 * Copyright by the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.syschallenge.mainservice.oauth.github;

import com.syschallenge.mainservice.oauth.OAuthProvider;
import com.syschallenge.mainservice.oauth.OAuthUserInfo;
import com.syschallenge.mainservice.oauth.github.request.GithubOAuthTokenRequest;
import com.syschallenge.mainservice.shared.api.GithubApi;
import com.syschallenge.mainservice.shared.api.response.GithubUser;
import com.syschallenge.mainservice.shared.property.GithubOAuthProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Github OAuth provider implementation
 *
 * @author panic08
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class GithubOAuthProvider implements OAuthProvider {

    private final GithubOAuthApi githubOAuthApi;
    private final GithubApi githubApi;
    private final GithubOAuthProperty properties;

    private static final Pattern TOKEN_PATTERN = Pattern.compile("access_token=([^&]+)");

    @Override
    public OAuthUserInfo extractUser(String code) {
        String accessToken = githubOAuthApi.requestToken(
                new GithubOAuthTokenRequest(
                        properties.getClientId(),
                        properties.getClientSecret(),
                        code,
                        properties.getRedirectUri()
                )
        );
        String extractedAccessToken = extractAccessToken(accessToken);
        GithubUser authorizedGithubUser = githubApi.getUser(extractedAccessToken);
        return new OAuthUserInfo(
                String.valueOf(authorizedGithubUser.id()),
                authorizedGithubUser.email()
        );
    }

    private String extractAccessToken(String input) {
        Matcher matcher = TOKEN_PATTERN.matcher(input);
        return matcher.find() ? matcher.group(1) : null;
    }
}
