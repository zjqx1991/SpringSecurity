package com.raven.demo.service.impl;

import com.raven.core.validate.pojo.RavenValidateCode;
import com.raven.core.validate.service.IRavenMobileValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("mobileValidateCodeGenerator")
public class DemoMobileCodeGenerator implements IRavenMobileValidateCodeGenerator {
    @Override
    public RavenValidateCode generator(ServletWebRequest request) {
        return new RavenValidateCode("111", 100);
    }
}
