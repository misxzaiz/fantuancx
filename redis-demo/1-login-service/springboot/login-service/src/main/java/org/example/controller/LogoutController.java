package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("logout")
//@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class LogoutController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("{token}")
    public R<String> get(@PathVariable("token") String token){
        redisTemplate.delete(token);
        log.info("删除：{}",token);
        return R.success("注销成功！");
    }
}