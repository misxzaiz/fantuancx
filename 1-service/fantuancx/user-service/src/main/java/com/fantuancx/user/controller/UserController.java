package com.fantuancx.user.controller;


import com.fantuancx.api.common.R;
import com.fantuancx.user.pojo.User;
import com.fantuancx.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        log.info("通过id（{}）查询用户",id);
        return userService.getById(id);
    }

    @GetMapping
    public R<List<User>> getUserList(){
        log.info("查询用户信息！");
        return R.success(userService.list(),"用户信息！");
    }

    @PutMapping
    public R<String> updateUser(@RequestBody User user){
        log.info("user:{}",user);

        boolean update = userService.updateById(user);
        if (update){
            return R.success("更新成功！");
        }
        return R.fail("更新失败！");
    }

    @PostMapping
    public R<String> saveUser(@RequestBody User user){
        log.info("user:{}",user);

        boolean save = userService.save(user);
        if (save){
            return R.success("添加成功！");
        }
        return R.fail("添加失败！");
    }


}
