package com.bibliotheque.bibliotheque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.bibliotheque.entities.ClientLogs;
import com.bibliotheque.bibliotheque.repositories.ClientLogsRepository;

@Service
public class ClientLogsService {
    
    @Autowired
    private ClientLogsRepository clientLogsRepository;

    public List<ClientLogs> getAllLogs() {
        return clientLogsRepository.findAll();
    }

    public void save(ClientLogs clientLogs) {
        clientLogsRepository.save(clientLogs);
    }
}
