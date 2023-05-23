package com.fantuancx.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fantuancx.api.common.R;
import com.fantuancx.user.pojo.User;
import com.fantuancx.user.pojo.UsersPage;
import com.fantuancx.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        List<User> users = userService.list();
        return R.success(users,"用户信息！");
    }

    @PutMapping
    public R<String> updateUser(@RequestBody User user){
        try {
            log.info("更新：user:{}",user);
            boolean update = userService.updateById(user);
            if (update){
                return R.success("添加成功！");
            }
            return R.fail("添加失败！");
        } catch (DuplicateKeyException e) {
            return R.fail("用户名已存在！");
        } catch (Exception e) {
            return R.fail("添加失败！");
        }
    }

    @PostMapping
    public R<String> saveUser(@RequestBody User user){
        try {
            log.info("新增：user:{}",user);
            boolean save = userService.save(user);
            if (save){
                return R.success("添加成功！");
            }
            return R.fail("添加失败！");
        } catch (DuplicateKeyException e) {
            return R.fail("用户名已存在！");
        } catch (Exception e) {
            return R.fail("添加失败！");
        }
    }

    @DeleteMapping("{id}")
    public R<String> deleteUserById(@PathVariable Long id){
        log.info("删除用户！{}",id);
        boolean mark = userService.removeById(id);
        if(mark){
            return R.success("删除成功！");
        }
        return R.fail("删除失败！");
    }

    @PostMapping("/batch-delete")
    public R<String> batchDelete(@RequestBody List<Long> ids) {
        log.info("删除用户！{}",ids);
        boolean mark = userService.removeBatchByIds(ids);
        if(mark){
            return R.success("删除成功！");
        }
        return R.fail("删除失败！");
    }

    @GetMapping("pages/{current}")
    public R<UsersPage> getUserByPage(@PathVariable Long current){
        try {
            IPage page = new Page(current,5);
            userService.page(page,null);
            UsersPage usersPage = new UsersPage();
            usersPage.setUsers(page.getRecords());
            usersPage.setPages(page.getPages());
            return R.success(usersPage,"用户信息获取成功！");
        } catch (Exception e) {
            return R.fail("用户信息获取失败！");
        }
    }
}
