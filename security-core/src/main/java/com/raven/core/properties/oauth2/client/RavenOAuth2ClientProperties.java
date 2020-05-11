package com.raven.core.properties.oauth2.client;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RavenOAuth2ClientProperties {
    private String clientId;
    private String clientSecret;
    private int accessTokenValiditySeconds = 7200;
}
