package com.raven.core.validate.service;

public interface IRavenMobileCodeSendService {
    /**
     * 给手机发送短信
     * 默认实现使用服务器随机生成验证码
     * 注入bean名称 "mobileCodeSend"
     */
    void send(String mobile, String code);
}
