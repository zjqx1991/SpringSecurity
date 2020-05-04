package com.raven.core.properties.validate;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class RavenImageValidateCodeProperties {
    /**
     *  在请求的连接中拼接参数可以改变图形验证码的大小
     *  raven_width：参数来改变图形验证码的宽
     *  raven_height：参数来改变图形验证码的高
     */
    private int width = 67;
    private int height = 23;
    private int length = 6;
    private int expireIn = 60;
    private List validateUrls = new ArrayList();
}
