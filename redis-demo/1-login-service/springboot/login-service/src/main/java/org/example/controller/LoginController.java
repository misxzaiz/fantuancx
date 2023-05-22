package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.R;
import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("login")
//@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class LoginController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping
    public R<String> login(@RequestBody User user){
        log.info("user:{}",user);
        if(user.getPhone().equals("fantuan") && user.getPassword().equals("1234")){
            redisTemplate.opsForValue().set("token","token",7, TimeUnit.DAYS);
            return R.success("token","登录成功！");
        }
        return R.fail("登录失败！");
    }

    @GetMapping
    public R<String> get(){
        log.info("测试！");
        return R.success("测试！");
    }
}
