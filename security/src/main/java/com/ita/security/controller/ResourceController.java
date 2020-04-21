package com.ita.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("/info")
    public ResponseEntity info(){
        return ResponseEntity.ok().body("success visit resource");
    }

}
