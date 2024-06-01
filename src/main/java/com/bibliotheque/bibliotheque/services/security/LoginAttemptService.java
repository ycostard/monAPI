package com.bibliotheque.bibliotheque.services.security;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.bibliotheque.bibliotheque.entities.Client;
import com.bibliotheque.bibliotheque.entities.ClientLogs;
import com.bibliotheque.bibliotheque.services.ClientLogsService;
import com.bibliotheque.bibliotheque.services.ClientService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class LoginAttemptService {

    public static final int MAX_ATTEMPT = 4;
    private LoadingCache<String, Integer> attemptsCache;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClientLogsService clientLogsService;

    @Autowired
    private ClientService clientService;
    
    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
    }

    public void loginFailed(final String key, Client client) {
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts); 
        if(attempts >= MAX_ATTEMPT) {
            clientService.updateLockedClient(client, false);
        }
        clientLogsService.save(new ClientLogs(client.getId(), getClientIP(), "Failed"));
    }

    public void loginSuccess(final String key, Client client){
        clientLogsService.save(new ClientLogs(client.getId(), getClientIP(), "Success"));
    }

    public boolean isBlocked() {
        try {
            return attemptsCache.get(getClientIP()) >= MAX_ATTEMPT;
        } catch (final ExecutionException e) {
            return false;
        }
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}