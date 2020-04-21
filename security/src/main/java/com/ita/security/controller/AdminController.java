package com.ita.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/info")
    public ResponseEntity info(){
        return ResponseEntity.ok().body("admin get info success");
    }
}
