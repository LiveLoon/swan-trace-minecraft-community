package org.swan_trace_mc_community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 为所有带有 @RestController 注解的类统一添加 /api 前缀
        configurer.addPathPrefix("/api",
                c -> c.isAnnotationPresent(RestController.class));
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{path:^(?!api|.*\\..*$).*$}")
                .setViewName("forward:/index.html");
    }
}