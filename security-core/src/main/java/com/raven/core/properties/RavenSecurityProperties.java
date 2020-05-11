package com.raven.core.properties;

import com.raven.core.properties.browser.RavenBrowserProperties;
import com.raven.core.properties.oauth2.RavenOAuth2Properties;
import com.raven.core.properties.session.RavenSessionProperties;
import com.raven.core.properties.social.RavenSocialProperties;
import com.raven.core.properties.validate.RavenValidateCodeProperties;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 系统配置类
 */
@Getter
@ConfigurationProperties("raven.security")
public class RavenSecurityProperties {
    // 浏览器相关配置
    private RavenBrowserProperties browser = new RavenBrowserProperties();
    // 验证码相关配置
    private RavenValidateCodeProperties validate = new RavenValidateCodeProperties();
    // 社交相关配置
    private RavenSocialProperties social = new RavenSocialProperties();
    // OAuth2相关配置
    private RavenOAuth2Properties oauth2 = new RavenOAuth2Properties();
}
