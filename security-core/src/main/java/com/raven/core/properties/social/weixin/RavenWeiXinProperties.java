package com.raven.core.properties.social.weixin;

import lombok.Getter;
import lombok.Setter;

/**
 * WX相关配置
 */
@Setter
@Getter
public class RavenWeiXinProperties {
    private String providerId = "weixin";
    private String appId;
    private String appSecret;
}
