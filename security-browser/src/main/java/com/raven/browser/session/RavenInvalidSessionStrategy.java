/**
 *
 */
package com.raven.browser.session;

import com.raven.core.properties.RavenSecurityProperties;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的session失效处理策略
 *
 * @author zhailiang
 *
 */
public class RavenInvalidSessionStrategy extends RavenAbstractSessionStrategy implements InvalidSessionStrategy {

	public RavenInvalidSessionStrategy(RavenSecurityProperties securityProperties) {
		super(securityProperties);
	}

	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
