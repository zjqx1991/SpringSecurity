package com.raven.core.validate.service;

import com.raven.core.validate.pojo.RavenImageCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface IRavenValidateCodeGenerator {

    /**
     * 生成图形验证码
     */
    RavenImageCode generator(ServletWebRequest request);
}
