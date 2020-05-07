package com.raven.core.social.qq.service.impl;

import com.raven.core.social.qq.connect.RavenQQOAuth2Template;
import com.raven.core.social.qq.service.IRavenQQService;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;


public class RavenQQServiceProvider extends AbstractOAuth2ServiceProvider<IRavenQQService> {

    private String appId;

    /**
     * 获取Authorization Code
     */
    private static final String url_authorize = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 通过Authorization Code获取Access Token
     */
    private static final String url_access_token = "https://graph.qq.com/oauth2.0/token";

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     *
     * @param oauth2Operations the OAuth2Operations template for conducting the OAuth 2 flow with the provider.
     */
    public RavenQQServiceProvider(String appId, String appSecret) {
        super(new RavenQQOAuth2Template(appId, appSecret, url_authorize, url_access_token));
        this.appId = appId;
    }

    @Override
    public IRavenQQService getApi(String accessToken) {
        return new RavenQQServiceImpl(accessToken, this.appId);
    }
}
