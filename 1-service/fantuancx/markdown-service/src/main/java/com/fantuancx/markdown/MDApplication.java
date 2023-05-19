package com.fantuancx.markdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2 // 开启 Swagger
public class MDApplication {
    public static void main(String[] args) {
        SpringApplication.run(MDApplication.class,args);
    }
}
