package com.fantuancx.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fantuancx.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    void testPages(){
        IPage page = new Page(1,5);
        System.out.println(userService.page(page,null));
        System.out.println("当前页码："+page.getCurrent());
        System.out.println("每页数量："+page.getSize());
        System.out.println("总页数："+page.getPages());
        System.out.println("数据条数："+page.getTotal());
        System.out.println("数据："+page.getRecords());
    }

}
