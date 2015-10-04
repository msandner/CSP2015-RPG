package org.csproject.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
@Configuration
@ComponentScan({"org.csproject.service", "org.csproject.controller", "org.csproject.view"})
public class SpringConfiguration {
}
