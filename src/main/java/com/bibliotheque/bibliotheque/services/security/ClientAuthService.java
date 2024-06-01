package com.bibliotheque.bibliotheque.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.bibliotheque.bibliotheque.entities.Client;
import com.bibliotheque.bibliotheque.repositories.ClientRepository;



@Service
public class ClientAuthService implements UserDetailsService{
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        Client client = clientRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("Log/Pass incorrect"));
        return new Client(client.getId(), client.getUsername(), client.getPassword(), client.getNom(), client.getPrenom(), client.getAdresse(), client.getProfil(), client.getRole(), client.isAccountNonLocked());
    }
}
