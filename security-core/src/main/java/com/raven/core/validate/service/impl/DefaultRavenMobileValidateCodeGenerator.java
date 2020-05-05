package com.raven.core.validate.service.impl;

import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.pojo.RavenValidateCode;
import com.raven.core.validate.service.IRavenMobileValidateCodeGenerator;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 默认的手机验证码生成器
 */
@Setter
public class DefaultRavenMobileValidateCodeGenerator implements IRavenMobileValidateCodeGenerator {

    private RavenSecurityProperties securityProperties;


    @Override
    public RavenValidateCode generator(ServletWebRequest request) {
        int expireIn = securityProperties.getValidate().getMobile().getExpireIn();
        int count = securityProperties.getValidate().getMobile().getCount();
        String code = RandomStringUtils.randomNumeric(count);
        return new RavenValidateCode(code, expireIn);
    }
}
