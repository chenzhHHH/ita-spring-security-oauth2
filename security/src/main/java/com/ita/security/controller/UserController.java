package com.ita.security.controller;

import com.ita.security.entity.User;
import com.ita.security.exception.AuthenticationFailedException;
import com.ita.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("phone") String phone, @RequestParam("password") String password) throws AuthenticationFailedException {
        System.out.println("helloworld");
        User user = userService.login(phone, password);
        return ResponseEntity.ok().body(user);
    }

}
