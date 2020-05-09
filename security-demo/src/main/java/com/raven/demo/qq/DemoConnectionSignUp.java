package com.raven.demo.qq;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

//@Component
public class DemoConnectionSignUp implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {
        /**
         * TODO
         * 根据connection信息创建第三方用户
         * 并且返回该用户数据中的唯一id
         * 把id返回，用户和QQ账号关联
         */
        // 返回唯一标识
        return connection.getDisplayName();
    }
}
