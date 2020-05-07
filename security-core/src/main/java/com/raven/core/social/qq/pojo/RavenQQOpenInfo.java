package com.raven.core.social.qq.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * 第三方QQ返回的用户信息
 * 这个类的字段，是参照 qq互联开放平台 get_user_info这个接口的返回值来的
 */
@Setter
@Getter
public class RavenQQOpenInfo {
    private String client_id;
    private String openid;
}
