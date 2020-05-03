package com.raven.demo.service.impl;

import com.raven.demo.mapper.IDemoUserMapper;
import com.raven.demo.pojo.DemoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IDemoUserMapper userMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
