package com.ita.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

    private static final String SECRET = "secret";
    private static final String RESOURCE_ID = "res1";
    private static final String CLIENT_ID = "client1";

    @Bean
    public ResourceServerTokenServices tokenService(){
        RemoteTokenServices service=new RemoteTokenServices();
        service.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        service.setClientId(CLIENT_ID);
        service.setClientSecret(SECRET);
        return service;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)//资源Id
                .tokenServices(tokenService())//验证令牌的服务
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/**").access("#oauth2.hasScope('all')")
                .and()
                .csrf()
                    .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
