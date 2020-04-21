package com.ita.security.controller;

import com.ita.security.entity.User;
import com.ita.security.entity.UserInfo;
import com.ita.security.exception.AuthenticationFailedException;
import com.ita.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity login(@RequestParam("userName") String userName, @RequestParam("password") String password) throws AuthenticationFailedException {
        User user = userService.login(userName, password);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/info")
    public ResponseEntity info(){
        UserInfo userInfo=new UserInfo();
        userInfo.setComment("normal user");
        userInfo.setAge(20);
        return ResponseEntity.ok().body(userInfo);
    }

    @GetMapping("/detail")
    public ResponseEntity detail(){
        UserInfo userInfo=new UserInfo();
        userInfo.setComment("admin user");
        userInfo.setAge(22);
        return ResponseEntity.ok().body(userInfo);
    }

    @GetMapping("/resource")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity resource(){
        return ResponseEntity.ok().body("success");
    }

}
