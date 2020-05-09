package com.raven.core.properties.social.qq;

import lombok.Getter;
import lombok.Setter;

/**
 * QQ相关配置
 */
@Setter
@Getter
public class RavenQQProperties {
    private String providerId = "qq";
    private String appId;
    private String appSecret;
}
