package com.raven.core.validate.service;

import com.raven.core.validate.pojo.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface IRavenValidateCodeGenerator {

    /**
     * 生成图形验证码
     */
    ImageCode generator(ServletWebRequest request);
}
