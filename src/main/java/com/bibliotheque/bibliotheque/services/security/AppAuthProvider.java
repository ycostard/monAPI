package com.bibliotheque.bibliotheque.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bibliotheque.bibliotheque.entities.Client;

public class AppAuthProvider implements AuthenticationManager {

    @Autowired
    private ClientAuthService clientAuthService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public Authentication authenticate(Authentication authentication)  {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String name = auth.getName();
        String password = auth.getCredentials().toString();

        Client client = (Client) clientAuthService.loadUserByUsername(name);

        if(client == null){
            throw new BadCredentialsException("Log/Pass incorrect");
        }

        if(!client.isAccountNonLocked()){
            throw new LockedException("Account is locked");
        }
        
        if(!passwordEncoder.matches(password, client.getPassword())){
            if(client != null) {
                applicationEventPublisher.publishEvent(new FailedLogEvent(this, client));
            }
            throw new BadCredentialsException("Log/Pass incorrect");
        }
        applicationEventPublisher.publishEvent(new SuccessLogEvent(this, client));

        return new UsernamePasswordAuthenticationToken(name, password, client.getAuthorities());
    }
    
}
