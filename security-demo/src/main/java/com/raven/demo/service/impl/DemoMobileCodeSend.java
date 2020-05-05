package com.raven.demo.service.impl;

import com.raven.core.validate.service.IRavenMobileCodeSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component("mobileCodeSend")
public class DemoMobileCodeSend implements IRavenMobileCodeSendService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void send(String mobile, String code) {
        logger.info(mobile);
        logger.info(code);
    }
}
