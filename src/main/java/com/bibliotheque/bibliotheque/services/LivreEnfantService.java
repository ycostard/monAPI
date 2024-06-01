package com.bibliotheque.bibliotheque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.bibliotheque.dtos.LivreEnfantDTO;
import com.bibliotheque.bibliotheque.entities.Auteur;
import com.bibliotheque.bibliotheque.entities.LivreEnfant;
import com.bibliotheque.bibliotheque.exceptions.NoDataFoundException;
import com.bibliotheque.bibliotheque.repositories.LivreEnfantRepository;

@Service
public class LivreEnfantService {

    @Autowired
    private LivreEnfantRepository livreEnfantRepository;

    @Autowired
    private AuteurService auteurService;

    public LivreEnfant createLivre(LivreEnfantDTO livre) {
        LivreEnfant livreToSave = new LivreEnfant(livre);
        livreToSave.setAuteur(auteurService.getRealAuteurById(livre.getIdAuteur()));
        
        livreEnfantRepository.save(livreToSave);
        return livreToSave;
    }

    public List<LivreEnfant> getAllLivres() {
        List<LivreEnfant> livres = livreEnfantRepository.findAll();
        if(livres.isEmpty()) throw new NoDataFoundException("Aucun Livre trouvé");
        return livres;
    }

    public LivreEnfant getLivreByIsbn(String isbn) {
        if(!livreEnfantRepository.existsById(isbn)) throw new NoDataFoundException("Livre avec l'isbn " + isbn + " non trouvé");
        return livreEnfantRepository.findById(isbn).get();
    }

    public LivreEnfant getLivreByTitre(String titre) {
        if(livreEnfantRepository.findByTitre(titre) == null) throw new NoDataFoundException("Livre avec le titre " + titre + " non trouvé");
        return livreEnfantRepository.findByTitre(titre);
    }
    
    public List<LivreEnfant> getLivresByAuteur(Auteur auteur) {
        List<LivreEnfant> livres = livreEnfantRepository.findByAuteur(auteur);
        if(livres.isEmpty()) throw new NoDataFoundException("Aucun Livre trouvé");
        return livres;
    }

    public LivreEnfant updateLivre(LivreEnfantDTO livre) {
        if(!livreEnfantRepository.existsById(livre.getIsbn())) throw new NoDataFoundException("Livre avec l'isbn " + livre.getIsbn() + " non trouvé");
        LivreEnfant livreToUpdate = livreEnfantRepository.findById(livre.getIsbn()).get();
        livreToUpdate.setTitre(livre.getTitre());
        livreToUpdate.setPrixHT(livre.getPrixHT());
        livreToUpdate.setAnnee(livre.getAnnee());
        livreToUpdate.setAge(livre.getAge());
        livreEnfantRepository.save(livreToUpdate);
        return livreToUpdate;
    }

    public void deleteLivre(String isbn) {
        if(!livreEnfantRepository.existsById(isbn)) throw new NoDataFoundException("Livre avec l'isbn " + isbn + " non trouvé");
        livreEnfantRepository.deleteById(isbn);
    }
}
