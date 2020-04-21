package com.ita.security.filter;

import com.ita.security.vo.UserVo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String TOKEN_ROLE = "Roles";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_SIGNING_KEY = "ita_security_demo";

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    public JWTLoginFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserVo loginUser = new UserVo();
        loginUser.setUsername(request.getParameter("username"));
        loginUser.setPassword(request.getParameter("password"));
        UserDetails currentUserDetails = userDetailsService.loadUserByUsername(loginUser.getUsername());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword(),
                        currentUserDetails.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User loginUser = (User) authResult.getPrincipal();
        System.out.println(authResult.getAuthorities().stream());
        String token = Jwts.builder()
                .setSubject(loginUser.getUsername())
                .claim(TOKEN_ROLE, authResult.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 30 * 1000))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNING_KEY)
                .compact();
        response.addHeader(HEADER_AUTHORIZATION, TOKEN_PREFIX + token);
    }

}
