package com.raven.browser.session;

import com.raven.core.properties.RavenSecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

public class RavenExpiredSessionStrategy extends RavenAbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public RavenExpiredSessionStrategy(RavenSecurityProperties securityProperties) {
        super(securityProperties);
    }

    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    /* (non-Javadoc)
     * @see com.imooc.security.browser.session.AbstractSessionStrategy#isConcurrency()
     */
    @Override
    protected boolean isConcurrency() {
        return true;
    }

}
