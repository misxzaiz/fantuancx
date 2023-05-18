package org.example.demo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo2")
public class Demo2Controller {

    @GetMapping
    public String demo2(){
        return "demo2";
    }
}
