package com.raven.core.validate.service;

import com.raven.core.validate.pojo.RavenValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface IRavenMobileValidateCodeGenerator {

    /**
     * 生成验证码
     * 请求注入名称为 "mobileValidateCodeGenerator"
     */
    RavenValidateCode generator(ServletWebRequest request);
}
