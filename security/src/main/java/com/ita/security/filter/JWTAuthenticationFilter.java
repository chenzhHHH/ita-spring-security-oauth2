package com.ita.security.filter;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_ROLE = "Roles";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_SIGNING_KEY = "ita_security_demo";

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);
        String user = Jwts.parser()
                .setSigningKey(TOKEN_SIGNING_KEY)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        if (user != null) {
            List<String> roles = Jwts.parser()
                    .setSigningKey(TOKEN_SIGNING_KEY)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .get(TOKEN_ROLE, List.class);
            return new UsernamePasswordAuthenticationToken(user, null,
                    roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        return null;
    }

}
