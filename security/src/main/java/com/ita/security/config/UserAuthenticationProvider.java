package com.ita.security.config;

import com.ita.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(username==null || username.length()==0){
            throw new UsernameNotFoundException("username用户名不可以为空");
        }
        if(password==null || password.length()==0){
            throw new BadCredentialsException("密码不可以为空");
        }
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new BadCredentialsException("password密码不正确");
        }
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
