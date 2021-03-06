package com.raven.demo.service.impl;

import com.raven.core.validate.pojo.RavenValidateCode;
import com.raven.core.validate.service.IRavenValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("smsValidateCodeGenerator")
public class DemoMobileCodeGenerator implements IRavenValidateCodeGenerator {
    @Override
    public RavenValidateCode generator(ServletWebRequest request) {
        return new RavenValidateCode("111", 100);
    }
}
