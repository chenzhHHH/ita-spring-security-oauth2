package com.ita.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/info")
    public ResponseEntity info(){
        return ResponseEntity.ok().body("hello world");
    }

}
