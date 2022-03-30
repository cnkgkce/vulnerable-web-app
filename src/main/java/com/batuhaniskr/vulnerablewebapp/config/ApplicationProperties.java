package com.batuhaniskr.vulnerablewebapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties("properties")
public class ApplicationProperties {

    private String path;

       public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
