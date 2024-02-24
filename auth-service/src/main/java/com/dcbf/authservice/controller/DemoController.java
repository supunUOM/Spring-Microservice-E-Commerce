package com.dcbf.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class DemoController {
//
//    @GetMapping()
//    public ResponseEntity<String> sayHello() {
//        User user = AuthUtils.getCurrentUser();
//        System.out.println("======================" + user);
//        return ResponseEntity.ok("Hello from secured endpoint");
//    }

    @GetMapping("/hello")
    String hello() {
        return "supun@gmail.com";
    }
}
