package com.raven.core.properties.validate;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class RavenMobileValidateCodeProperties {
    // 验证码个数
    private int count = 6;
    // 验证码过期时间
    private int expireIn = 10;
    // 需要验证的url
    private List validateUrls = new ArrayList();
}
