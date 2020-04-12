package com.ita.security.service;

import com.ita.security.entity.User;
import com.ita.security.exception.AuthenticationFailedException;
import com.ita.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User login(String phone, String password) throws AuthenticationFailedException {
        User user = userRepository.findByPhone(phone);
        if(user==null || !user.getPassword().equals(password)){
            throw new AuthenticationFailedException();
        }
        return user;
    }

}
