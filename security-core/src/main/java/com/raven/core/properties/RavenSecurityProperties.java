package com.raven.core.properties;

import com.raven.core.properties.browser.RavenBrowserProperties;
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

}