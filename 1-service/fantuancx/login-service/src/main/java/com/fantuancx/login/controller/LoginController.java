package com.fantuancx.login.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.fantuancx.api.common.R;
import com.fantuancx.api.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {
    @PostMapping
    public R<String> login(@RequestBody User user){
        log.info("User:{}",user);
        if(user.getUsername().equals("fantuan") && user.getPassword().equals("1234")){
            // 获取 JWT 令牌
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE, 7);
            JWTCreator.Builder builder = JWT.create();
            builder.withClaim("username",user.getUsername())
                    .withClaim("password",user.getPassword());
            String jwt = builder.withExpiresAt(instance.getTime())
                    .sign(Algorithm.HMAC256("fantuan"));
            log.info("JWT:{}",jwt);
            return R.success(jwt,"登录成功！");
        }
        return R.fail("登录失败！");
    }
}
