package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.LoginFormDTO;
import org.example.dto.Result;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.service.IUserService;
import org.example.utils.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @GetMapping("code/{phone}")
    public Result getCode(@PathVariable String phone){
        log.info("获取验证码的手机号为：{}",phone);
        return userService.sendCode(phone);
    }

    /**
     * 登录
     * @param loginFormDTO
     * @param session
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginFormDTO loginFormDTO){
        log.info("user/login（post）：{}",loginFormDTO);
        return userService.login(loginFormDTO);
    }

    /**
     * 获取当前用户信息并返回
     * @return
     */
    @GetMapping("/me")
    public Result getMe(){
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }





}
