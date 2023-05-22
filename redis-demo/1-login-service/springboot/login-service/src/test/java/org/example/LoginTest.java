package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class LoginTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void set(){
        // 过期时间 7 天
        redisTemplate.opsForValue().set("token","token",7, TimeUnit.DAYS);
    }

    @Test
    public void get(){
        System.out.println(redisTemplate.opsForValue().get("token"));
    }

}
