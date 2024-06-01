package com.bibliotheque.bibliotheque.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.bibliotheque.dtos.AuteurDTO;
import com.bibliotheque.bibliotheque.entities.Auteur;
import com.bibliotheque.bibliotheque.exceptions.NoDataFoundException;
import com.bibliotheque.bibliotheque.repositories.AuteurRepository;

@Service
public class AuteurService {

    private final AuteurRepository auteurRepository;

    @Autowired
    public AuteurService(AuteurRepository auteurRepository){
        this.auteurRepository = auteurRepository;
    }


    public AuteurDTO createAuteur(AuteurDTO auteur) {
        auteurRepository.save(new Auteur(auteur));
        return auteur;
    }

    public List<AuteurDTO> getAllAuteurs() {
        List<AuteurDTO> auteurs = new ArrayList<>();
        auteurRepository.findAll().forEach(auteur -> auteurs.add(new AuteurDTO(auteur)));
        if(auteurs.isEmpty()) throw new NoDataFoundException("Aucun Auteur trouvé");
        return auteurs;
    }

    public AuteurDTO getAuteurById(int id) {
        if(!auteurRepository.existsById(id)) throw new NoDataFoundException("Auteur avec l'id " + id + " non trouvé");
        return new AuteurDTO(auteurRepository.findById(id).get());
    }

    public Auteur getRealAuteurById(int id) {
        if(!auteurRepository.existsById(id)) throw new NoDataFoundException("Auteur avec l'id " + id + " non trouvé");
        return auteurRepository.findById(id).get();
    }

    public AuteurDTO updateAuteur(int id, AuteurDTO auteur) {
        if(!auteurRepository.existsById(id)) throw new NoDataFoundException("Auteur avec l'id " + id + " non trouvé");
        Auteur auteurToUpdate = auteurRepository.findById(id).get();
        auteurToUpdate.setNom(auteur.getNom());
        auteurToUpdate.setPrenom(auteur.getPrenom());
        auteurToUpdate.setPseudo(auteur.getPseudo());
        auteurRepository.save(auteurToUpdate);
        return auteur;
    }

    public void deleteAuteur(int id) {
        if(!auteurRepository.existsById(id)) throw new NoDataFoundException("Auteur avec l'id " + id + " non trouvé");
        auteurRepository.deleteById(id);
    }
}
