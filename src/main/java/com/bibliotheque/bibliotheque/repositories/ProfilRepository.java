package com.bibliotheque.bibliotheque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliotheque.bibliotheque.entities.Profil;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Integer> {
    
}
