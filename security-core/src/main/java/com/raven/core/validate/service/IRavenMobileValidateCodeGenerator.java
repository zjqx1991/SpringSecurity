package com.raven.core.validate.service;

import com.raven.core.validate.pojo.RavenValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface IRavenMobileValidateCodeGenerator {

    /**
     * 生成验证码
     */
    RavenValidateCode generator(ServletWebRequest request);
}
