package com.bibliotheque.bibliotheque.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliotheque.bibliotheque.entities.Client;
import com.bibliotheque.bibliotheque.entities.Commande;
import com.bibliotheque.bibliotheque.enums.CommandeStatus;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    
    List<Commande> findByDateAndStatusAndClient(Date date, CommandeStatus status, Client client);
    List<Commande> findByDateAndStatus(Date date, CommandeStatus status);
    List<Commande> findByDateAndClient(Date date, Client client);
    List<Commande> findByStatusAndClient(CommandeStatus status, Client client);
    List<Commande> findByDate(Date date);
    List<Commande> findByStatus(CommandeStatus status);
    List<Commande> findByClient(Client client);
}
