package com.raven.app.config;

import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.properties.oauth2.client.RavenOAuth2ClientProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AppAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RavenSecurityProperties securityProperties;
    @Autowired
    private TokenStore redisTokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    /***
     * 入口点相关配置  ---  token的生成，存储方式等在这里配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(this.redisTokenStore)
                .authenticationManager(this.authenticationManager)
                .userDetailsService(this.userDetailsService);

        if (jwtAccessTokenConverter != null && this.jwtTokenEnhancer != null) {
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(this.jwtTokenEnhancer);
            enhancers.add(this.jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers)
            ;
            endpoints
                    .tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        RavenOAuth2ClientProperties[] clientsList = this.securityProperties.getOauth2().getClients();

        if (ArrayUtils.isNotEmpty(clientsList)) {
            for (RavenOAuth2ClientProperties c : clientsList) {
                String clientId = c.getClientId();
                String clientSecret = this.passwordEncoder.encode(c.getClientSecret());
                int accessTokenValiditySeconds = c.getAccessTokenValiditySeconds();
                builder.withClient(clientId)
                        .secret(clientSecret)
                        .accessTokenValiditySeconds(accessTokenValiditySeconds)
                        .authorizedGrantTypes("refresh_token", "password")
                        .refreshTokenValiditySeconds(2592000)
                        .scopes("all");
            }
        }

    }
}
