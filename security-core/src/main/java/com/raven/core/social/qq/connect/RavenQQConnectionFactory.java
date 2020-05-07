package com.raven.core.social.qq.connect;

import com.raven.core.social.qq.service.IRavenQQService;
import com.raven.core.social.qq.service.impl.RavenQQServiceProvider;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class RavenQQConnectionFactory extends OAuth2ConnectionFactory<IRavenQQService> {

    public RavenQQConnectionFactory(String providerId, String appId, String appSecret) {
        super(
                providerId,
                new RavenQQServiceProvider(appId, appSecret),
                new RavenQQApiAdapter()
                );
    }
}
