package com.raven.core.validate.service.impl;

import com.raven.core.validate.service.IRavenMobileCodeSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 由于 IRavenMobileCodeSendService 需要外接来实现发送短信验证码到手机
 * 所以这里就不能直接入住的spring容器中，需要通过Bean的方式来配置注入
 */
public class DefaultRavenMobileCodeSendServiceImpl implements IRavenMobileCodeSendService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void send(String mobile, String code) {
        logger.info("发送验证码：" + code + " 到手机：" + mobile + " 请注意查收");
    }
}
