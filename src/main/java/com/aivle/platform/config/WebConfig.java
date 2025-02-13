package com.aivle.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(
                        "file:" + new File("src/main/resources/static/uploads").getAbsolutePath() + "/");

        // anomalies 폴더에 대한 정적 리소스 매핑
        registry.addResourceHandler("/anomalies/**")
                .addResourceLocations("file:" + new File("anomalies").getAbsolutePath() + "/");

        // crime_alerts 폴더에 대한 정적 리소스 매핑
        registry.addResourceHandler("/crime_alerts/**")
                .addResourceLocations("file:" + new File("crime_alerts").getAbsolutePath() + "/");
    }

}
