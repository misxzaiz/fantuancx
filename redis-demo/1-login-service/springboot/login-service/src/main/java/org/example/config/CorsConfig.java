package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许跨域访问的路径
                // .allowedOrigins("*") // 允许跨域访问的源
                .allowedOrigins("http://localhost:5173") // 允许跨域访问的源，这里设置为允许127.0.0.1:5173的源
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE") // 允许请求方法
                .maxAge(168000) // 预检请求的有效期，单位为秒
                .allowedHeaders("*")  // 允许头部设置，多个逗号分隔
                .allowCredentials(true); // 是否允许发送cookie
    }
}
