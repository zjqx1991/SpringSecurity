package com.raven.demo.service.impl;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.validate.pojo.RavenValidateCode;
import com.raven.core.validate.service.impl.abstractRavenValidateCodeProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("smsValidateCodeProcessor")
public class DemoMobileValidateCodeProcessor extends abstractRavenValidateCodeProcessorService<RavenValidateCode> {

    @Autowired
    private DemoMobileCodeSend mobileCodeSend;

    @Override
    protected void send(ServletWebRequest request, RavenValidateCode validateCode) throws Exception {
        System.out.println("自定义短信粗利器");
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), RavenSecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        this.mobileCodeSend.send(mobile, validateCode.getCode());
    }
}
