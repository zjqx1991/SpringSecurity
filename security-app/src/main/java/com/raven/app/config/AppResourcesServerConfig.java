package com.raven.app.config;


import com.raven.app.social.openid.config.APPOpenIdAuthenticationSecurityConfig;
import com.raven.core.authorize.IRavenAuthorizeConfigManager;
import com.raven.core.config.RavenValidateCodeSecurityConfig;
import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.mobile.config.RavenMobileCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 资源服务器配置
 */
@Configuration
@EnableResourceServer
public class AppResourcesServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private RavenSecurityProperties securityProperties;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    // 验证码配置
    @Autowired
    private RavenValidateCodeSecurityConfig validateCodeSecurityConfig;
    // 短信配置
    @Autowired
    private RavenMobileCodeAuthenticationSecurityConfig mobileCodeConfig;
    // 社交配置
    @Autowired
    private SpringSocialConfigurer socialConfigurer;
    // openId
    @Autowired
    private APPOpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
    @Autowired
    private IRavenAuthorizeConfigManager authorizeConfigManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 配置登录界面
        String loginPage = this.securityProperties.getBrowser().getLoginPage();
        int tokenTime = this.securityProperties.getBrowser().getTokenTime();
        String signUpUrl = this.securityProperties.getBrowser().getSignUpUrl();

        // 表单配置
//        this.formAuthenticationConfig.configure(http);
        http.formLogin()
                .loginPage(RavenSecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(RavenSecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(successHandler)
                .failureHandler(failureHandler);

        // 验证码配置
        http.apply(this.validateCodeSecurityConfig);
        // 短信配置
        http.apply(this.mobileCodeConfig);
        // 社交配置
        http.apply(this.socialConfigurer);
        // openId
        http.apply(this.openIdAuthenticationSecurityConfig);
        http.csrf().disable();
        this.authorizeConfigManager.config(http.authorizeRequests());
    }
}
