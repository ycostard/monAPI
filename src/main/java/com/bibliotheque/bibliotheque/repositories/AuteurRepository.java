package com.bibliotheque.bibliotheque.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bibliotheque.bibliotheque.entities.Auteur;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Integer> {
    
}
