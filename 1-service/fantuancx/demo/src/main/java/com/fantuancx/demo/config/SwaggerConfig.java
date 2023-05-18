package com.fantuancx.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 // 开启 Swagger
public class SwaggerConfig {

    // 配置 Swagger 的 Docket 的 Bean 实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable((false))
                .apiInfo(apiInfo())
                .select()
                /**
                 * apis()：扫描
                 * RequestHandlerSelectors：配置要扫描接口的方式
                 * basePackage：指定要扫描的包
                 * any：扫描全部
                 * none：不扫描
                 * withClassAnnotation：扫描类上的注解，参数是一个注解的反射对象
                 * withMethodAnnotation：扫描方法上的注解
                 */
                .apis(RequestHandlerSelectors.basePackage("com.fantuancx.demo.controller"))
                // .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                // .apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class))
                /**
                 * paths()：过滤
                 * PathSelectors
                 * ant：指定扫描接口
                 */
                .paths(PathSelectors.ant("/demo/**"))
                .build();
    }

    // 配置 Swagger 信息 apiInfo
    private ApiInfo apiInfo(){
        // 作者信息
        Contact contact = new Contact("饭团", "URL", "邮箱");
        return new ApiInfo("Demo Api 文档","Demo Api 文档","v1.0",
                "http://xiaozaiz.top",contact,"Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",new ArrayList());
    }
}
