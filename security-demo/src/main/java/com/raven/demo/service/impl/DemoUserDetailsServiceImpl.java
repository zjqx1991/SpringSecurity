package com.raven.demo.service.impl;

import com.raven.demo.mapper.IDemoUserMapper;
import com.raven.demo.pojo.DemoUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DemoUserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {


    @Autowired
    private IDemoUserMapper userMapper;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("表单登录成功：" + username);
        return this.buildUserDetails(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        this.log.info("社交登录成功：" + userId);
        SocialUser socialUser = null;
        try {
            DemoUserDetails userDetails = this.buildUserDetails(userId);
            socialUser = new SocialUser(userDetails.getUsername(), userDetails.getPassword(),
                    userDetails.isEnabled(), true, true, true, userDetails.getAuthorities());
        } catch (Exception e) {
//            e.printStackTrace();
            return new SocialUser(userId, "123456",
                    true, true, true, true,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        }
        return socialUser;
    }


    /**
     * 从数据库中查询用户信息，来构建UserDetails
     *
     * @param username 可能是 username 或 userId
     * @return
     */
    private DemoUserDetails buildUserDetails(String username) {
        // 通过用户名获取用户信息
        DemoUserDetails userInfo = this.userMapper.fetchUserInfoByUserName(username);

        if (userInfo == null) {
            throw new RuntimeException("用户不存在");
        }

        // 通过用户名获取用户角色资源
        List<String> roleCodes = this.userMapper.fetchRoleCodesByUserName(username);

        // 通过用户名获取用户权限
        List<String> authorties = this.userMapper.fetchMenuUrlsByUserName(username);

        // 角色也是一种用户权限
        if (authorties != null && roleCodes != null) {
            authorties.addAll(roleCodes);
        }

        userInfo.setAuthorities(
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                        String.join(",", authorties)
                )
        );
        return userInfo;
    }
}
