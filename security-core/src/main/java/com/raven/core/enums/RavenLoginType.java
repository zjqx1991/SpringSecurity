package com.raven.core.enums;


import com.raven.core.constants.RavenSecurityConstants;

/**
 *
 * 登录返回类型
 *
 * @author zhailiang
 *
 */
public enum RavenLoginType {

	/**
	 * JSON格式返回
	 */
	JSON {
		@Override
		public String getParamNameOnValidate() {
			return RavenSecurityConstants.LOGIN_TYPE_JSON;
		}
	},
	/**
	 * JSON格式REDIRECT
	 */
    REDIRECT {
		@Override
		public String getParamNameOnValidate() {
			return RavenSecurityConstants.LOGIN_TYPE_REDIRECT;
		}
	};

	/**
	 * 校验时从请求中获取的参数的名字
	 * @return
	 */
	public abstract String getParamNameOnValidate();

}
