package com.raven.core.properties.oauth2;

import com.raven.core.properties.oauth2.client.RavenOAuth2ClientProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RavenOAuth2Properties {
    private String signingKey = "raven";
    private RavenOAuth2ClientProperties[] clients;
}
