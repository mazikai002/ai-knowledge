package com.itheima.backend.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloAPI {

    @GetMapping("aa")
    public String aa(){
        System.out.println("aa");
        return "aa";
    }
}
