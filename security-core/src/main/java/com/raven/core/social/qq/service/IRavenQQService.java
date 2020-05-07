package com.raven.core.social.qq.service;

import com.raven.core.social.qq.pojo.RavenQQUserInfo;

/**
 * 获取第三方QQ用户信息
 */
public interface IRavenQQService {

    /**
     * 获取第三方QQ用户信息
     */
    RavenQQUserInfo fetchQQUserInfo();
}
