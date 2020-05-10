package com.raven.core.constants;

public interface RavenSecurityConstants {

    /**
     * 登录成功/失败返回JSON
     */
    String LOGIN_TYPE_JSON = "JSON";

    /**
     * 登录成功/失败默认跳转
     */
    String LOGIN_TYPE_REDIRECT = "REDIRECT";

    /**
     * 验证码处理器Bean后缀
     */
    String VALIDATE_CODE_PROCESSOR_KEY_SUFFIX = "ValidateCodeProcessor";


    /**
     * 验证码生成器Bean后缀
     */
    String VALIDATE_CODE_GENERATOR_KEY_SUFFIX = "ValidateCodeGenerator";


    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code/";
    /**
     * 当请求需要身份认证时，默认跳转的url
     *
     * @see BrowserController
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";
    /**
     * 默认的手机验证码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 默认登录页面
     *
     * @see SecurityController
     */
    String DEFAULT_SIGN_IN_PAGE_URL = "/bw-login.html";
    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
    /**
     * session失效默认的跳转地址
     */
    String DEFAULT_SESSION_INVALID_URL = "/bw-session-invalid.html";
    /**
     * 获取第三方用户信息的url
     */
    String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";
    /**
     * 默认的OPENID登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_OPENID = "/authentication/openid";
    /**
     * openid参数名
     */
    String DEFAULT_PARAMETER_NAME_OPENID = "openId";
    /**
     * providerId参数名
     */
    String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";

}
