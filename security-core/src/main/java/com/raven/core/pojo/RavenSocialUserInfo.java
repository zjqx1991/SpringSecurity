package com.raven.core.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * 社交用户信息
 */
@Setter
@Getter
public class RavenSocialUserInfo {
    private String providerId;
    private String providerUserId;
    private String nickName;
    private String headImg;
}
