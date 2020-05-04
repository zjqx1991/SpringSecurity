package com.raven.core.properties.browser;

import com.raven.core.enums.RavenLoginType;
import lombok.Data;

/**
 * 浏览器相关配置类
 */
@Data
public class RavenBrowserProperties {
    private String loginPage = "/bw-login.html";
    private RavenLoginType loginType = RavenLoginType.JSON;
}
