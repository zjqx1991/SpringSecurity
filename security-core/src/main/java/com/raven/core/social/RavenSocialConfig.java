package com.raven.core.social;

import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.social.jdbc.RavenJdbcUsersConnectionRepository;
import com.raven.core.social.qq.config.RavenSpringSocialConfigurer;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableSocial
public class RavenSocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private HikariDataSource hikariDataSource;
    @Autowired
    private RavenSecurityProperties securityProperties;
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // 第三个参数是对插入到数据库中的数据做加密，这里为了看的清楚，没有做任何处理，即noOpText
        RavenJdbcUsersConnectionRepository repository = new RavenJdbcUsersConnectionRepository(this.hikariDataSource, connectionFactoryLocator, Encryptors.noOpText());
//        repository.setTablePrefix("t_");
        if (this.connectionSignUp != null) {
            repository.setConnectionSignUp(this.connectionSignUp);
        }
        return repository;
    }

    @Bean
    public SpringSocialConfigurer ravenSocialSecurityConfig() {
        String filterProcessesUrl = this.securityProperties.getSocial().getQq().getFilterProcessesUrl();
        String signUpUrl = this.securityProperties.getBrowser().getSignUpUrl();
        RavenSpringSocialConfigurer configurer = new RavenSpringSocialConfigurer(filterProcessesUrl);
        configurer.signupUrl(signUpUrl);
        return configurer;
    }

    /**
     * ProviderSignInUtils有两个作用：
     * （1）从session里获取封装了第三方用户信息的Connection对象
     * （2）将注册的用户信息与第三方用户信息（QQ信息）建立关系并将该关系插入到userconnection表里
     * <p>
     * 需要的两个参数：
     * （1）ConnectionFactoryLocator 可以直接注册进来，用来定位ConnectionFactory
     * （2）UsersConnectionRepository----》getUsersConnectionRepository(connectionFactoryLocator)
     * 即我们配置的用来处理userconnection表的bean
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)) {
        };
    }
}
