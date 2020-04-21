package com.ita.security.service;

import com.ita.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password=new BCryptPasswordEncoder().encode("123");
        if(username.equals("admin")){
            return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        }
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }

}

