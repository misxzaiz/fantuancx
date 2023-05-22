package org.example.gateway.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.gateway.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {


    @PostMapping
    public String login(@RequestBody User user){
        if(user.getUsername().equals("fantuan") && user.getPassword().equals("1234")){
            Map<String,Object> map = new HashMap<>();
            map.put("username",user.getUsername());
            map.put("password",user.getPassword());
            String jwt = JWTUtil.setJWT(7,map);
            return jwt;
        }
        return "账号/密码错误";
    }

    @Data
    static class User {
        private String username;
        private String password;
    }
}
