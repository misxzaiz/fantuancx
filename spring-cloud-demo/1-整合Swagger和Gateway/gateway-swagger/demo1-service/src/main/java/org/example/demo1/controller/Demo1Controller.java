package org.example.demo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo1")
public class Demo1Controller {
    @GetMapping
    public String demo1(){
        return "demo1";
    }
}