package com.fantuancx.gateway.controller;

import com.fantuancx.api.common.R;
import com.fantuancx.api.pojo.User;
import com.fantuancx.gateway.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @PostMapping
    public R<String> login(@RequestBody User user){
        if(user.getUsername().equals("fantuan") && user.getPassword().equals("1234")){
            Map<String,Object> map = new HashMap<>();
            map.put("username",user.getUsername());
            map.put("password",user.getPassword());
            String jwt = JWTUtil.setJWT(7,map);
            return R.success(jwt,"登录成功！");
        }
        return R.fail("账号/密码错误");
    }
    
}
