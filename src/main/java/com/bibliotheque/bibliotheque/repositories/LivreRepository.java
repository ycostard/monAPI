package com.bibliotheque.bibliotheque.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliotheque.bibliotheque.entities.Auteur;
import com.bibliotheque.bibliotheque.entities.Livre;

@Repository
public interface LivreRepository extends JpaRepository<Livre, String>{
    Livre findByTitre(String titre);

    List<Livre> findByAuteur(Auteur auteur);
}
