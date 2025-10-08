package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @GetMapping(value = "/")
    public String index() {
        return "アクセス成功です!";
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping(value = "/welcome")
    public String welcome() {
        return "Spring Bootへようこそ!";
    }
}