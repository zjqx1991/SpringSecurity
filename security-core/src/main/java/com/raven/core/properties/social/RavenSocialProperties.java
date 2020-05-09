package com.raven.core.properties.social;

import com.raven.core.properties.social.qq.RavenQQProperties;
import com.raven.core.properties.social.weixin.RavenWeiXinProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RavenSocialProperties {
    private String filterProcessesUrl = "/auth";
    private RavenQQProperties qq = new RavenQQProperties();
    private RavenWeiXinProperties wx = new RavenWeiXinProperties();
}
