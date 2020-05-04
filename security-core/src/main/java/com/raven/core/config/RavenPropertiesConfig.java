package com.raven.core.config;

import com.raven.core.properties.RavenSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RavenSecurityProperties.class)
public class RavenPropertiesConfig {
}
