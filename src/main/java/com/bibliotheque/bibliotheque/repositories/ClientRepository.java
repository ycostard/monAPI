package com.bibliotheque.bibliotheque.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliotheque.bibliotheque.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByUsername(String username);
    
    boolean existsByUsername(String username);

}
