package com.raven.core.validate.service.impl;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.validate.pojo.RavenValidateCode;
import com.raven.core.validate.service.IRavenMobileCodeSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


public class DefaultRavenSmsCodeProcessor extends abstractRavenValidateCodeProcessorService<RavenValidateCode> {

    @Autowired
    private IRavenMobileCodeSendService smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, RavenValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), RavenSecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
