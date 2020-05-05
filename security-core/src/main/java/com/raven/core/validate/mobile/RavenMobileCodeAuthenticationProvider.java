package com.raven.core.validate.mobile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Setter
@Getter
public class RavenMobileCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RavenMobileCodeAuthenticationToken mobileToken = (RavenMobileCodeAuthenticationToken) authentication;
        // 通过手机号来验证
        UserDetails userDetails = this.userDetailsService.loadUserByUsername((String) mobileToken.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        /**
         * 到这里认证通过
         * 通过重新创建RavenMobileCodeAuthenticationToken，使用2个参数的构造器
         * 这样认证标识会被设置为true
         */
        RavenMobileCodeAuthenticationToken mobileAuthenticationToken = new RavenMobileCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        // 重新赋值
        mobileAuthenticationToken.setDetails(mobileToken.getDetails());
        return mobileAuthenticationToken;
    }

    /**
     * AuthenticationManager 选择要执行的Provider
     * 这里就决定了不同的 Provider 执行不同的 Token
     * RavenMobileCodeAuthenticationToken
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (RavenMobileCodeAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
