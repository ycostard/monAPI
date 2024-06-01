package com.bibliotheque.bibliotheque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliotheque.bibliotheque.entities.Auteur;
import com.bibliotheque.bibliotheque.entities.LivreEnfant;

@Repository
public interface LivreEnfantRepository extends JpaRepository<LivreEnfant, String> {

    LivreEnfant findByTitre(String titre);

    List<LivreEnfant> findByAuteur(Auteur auteur);
    
}
