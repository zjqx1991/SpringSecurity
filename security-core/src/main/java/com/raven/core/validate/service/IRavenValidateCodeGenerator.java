package com.raven.core.validate.service;

import com.raven.core.validate.pojo.RavenValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface IRavenValidateCodeGenerator {

    /**
     * 生成验证码
     * 图片验证码bean："imageValidateCodeGenerator"
     * 短信验证码bean："smsValidateCodeGenerator"
     */
    RavenValidateCode generator(ServletWebRequest request);
}
