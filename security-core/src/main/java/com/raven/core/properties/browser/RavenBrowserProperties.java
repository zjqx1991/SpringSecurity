package com.raven.core.properties.browser;

import com.raven.core.enums.RavenLoginType;
import com.raven.core.properties.session.RavenSessionProperties;
import lombok.Data;

/**
 * 浏览器相关配置类
 */
@Data
public class RavenBrowserProperties {
    // Session相关配置
    private RavenSessionProperties session = new RavenSessionProperties();
    private String loginPage = "/bw-login.html";
    private String signUpUrl = "/bw-signUp.html";
    private String signInPage = "/bw-signUp.html";
    private String signOutUrl = "/bw-signUp.html";
    private RavenLoginType loginType = RavenLoginType.JSON;
    private int tokenTime = 3600;
}
