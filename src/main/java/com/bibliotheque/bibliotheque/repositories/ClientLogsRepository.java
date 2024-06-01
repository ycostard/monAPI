package com.bibliotheque.bibliotheque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.bibliotheque.entities.ClientLogs;

public interface ClientLogsRepository extends JpaRepository<ClientLogs, Integer> {
    
}
