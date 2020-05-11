package com.raven.app.config;

import com.raven.app.jwt.AppJwtTokenEnhancer;
import com.raven.core.properties.RavenSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class AppTokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "raven.security.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(this.redisConnectionFactory);
    }


    /**
     * 默认 使用 JWTToken 来换默认的token
     */
    @Configuration
    @ConditionalOnProperty(prefix = "raven.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JWTTokenConfig {

        @Autowired
        private RavenSecurityProperties securityProperties;


        @Bean
        public TokenStore jwtTokenStroe() {
            return new JwtTokenStore(this.jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
            tokenConverter.setSigningKey(this.securityProperties.getOauth2().getSigningKey());
            return tokenConverter;
        }

        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer() {
            return new AppJwtTokenEnhancer();
        }

    }
}
