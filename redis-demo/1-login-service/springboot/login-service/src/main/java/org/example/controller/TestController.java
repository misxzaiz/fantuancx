package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.R;
import org.example.pojo.User;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public R<String> get(){
        log.info("测试！");
        return R.success("测试！");
    }

}