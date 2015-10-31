package org.csproject.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
@Configuration
@ComponentScan({"org.csproject.service", "org.csproject.controller", "org.csproject.view"})
public class SpringConfiguration {

    @Bean
    public Gson gson()
    {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
