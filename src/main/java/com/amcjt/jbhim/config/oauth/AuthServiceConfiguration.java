package com.amcjt.jbhim.config.oauth;

import com.amcjt.jbhim.exception.AuthExceptionHandler;
import com.amcjt.jbhim.service.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthServiceConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager myAuthenticationManager;
    @Resource
    private UserDetail userDetailsService;
    @Resource
    BCryptPasswordEncoder passwordEncoder;
    @Resource
    private AuthExceptionHandler authExceptionHandler;

    @Bean
    public BCryptPasswordEncoder cryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder)
                .authenticationEntryPoint(authExceptionHandler)
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String finalPassword = passwordEncoder.encode("123456");
        clients
                .inMemory()
                .withClient("client_1")
                .secret(finalPassword)
                .authorizedGrantTypes("password")
                .scopes("read")
                .accessTokenValiditySeconds(720000);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(new InMemoryTokenStore())
                .authenticationManager(myAuthenticationManager)
                .userDetailsService(userDetailsService);
        endpoints.exceptionTranslator(authExceptionHandler);
    }
}
