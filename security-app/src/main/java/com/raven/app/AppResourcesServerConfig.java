package com.raven.app;


import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.properties.RavenSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
//        http.apply(this.validateCodeSecurityConfig);
        // 短信配置
//        http.apply(this.mobileCodeConfig);
        // 社交配置
//        http.apply(this.socialConfigurer);
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers(
                        RavenSecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        RavenSecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
                        loginPage,
                        RavenSecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "*",
                        signUpUrl,
                        "/user/regist"
                ).permitAll()
                .anyRequest()
                .authenticated();
    }
}
