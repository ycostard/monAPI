package com.bibliotheque.bibliotheque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.bibliotheque.dtos.LivreDTO;
import com.bibliotheque.bibliotheque.entities.Auteur;
import com.bibliotheque.bibliotheque.entities.Livre;
import com.bibliotheque.bibliotheque.exceptions.NoDataFoundException;
import com.bibliotheque.bibliotheque.repositories.LivreRepository;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private AuteurService auteurService;

    public Livre createLivre(LivreDTO livre) {
        Livre livreToSave = new Livre(livre);
        livreToSave.setAuteur(auteurService.getRealAuteurById(livre.getIdAuteur()));
        livreRepository.save(livreToSave);
        return livreToSave;
    }

    public List<Livre> getAllLivres() {
        List<Livre> livres = livreRepository.findAll();
        if(livres.isEmpty()) throw new NoDataFoundException("Aucun Livre trouvé");
        return livres;
    }

    public Livre getLivreByIsbn(String isbn) {
        if(!livreRepository.existsById(isbn)) throw new NoDataFoundException("Livre avec l'isbn " + isbn + " non trouvé");
        return livreRepository.findById(isbn).get();
    }

    public Livre getLivreByTitre(String titre) {
        if(livreRepository.findByTitre(titre) == null) throw new NoDataFoundException("Livre avec le titre " + titre + " non trouvé");
        return livreRepository.findByTitre(titre);
    }
    
    public List<Livre> getLivresByAuteur(Auteur auteur) {
        List<Livre> livres = livreRepository.findByAuteur(auteur);
        if(livres.isEmpty()) throw new NoDataFoundException("Aucun Livre trouvé");
        return livres;
    }

    public Livre updateLivre(LivreDTO livre) {
        if(!livreRepository.existsById(livre.getIsbn())) throw new NoDataFoundException("Livre avec l'isbn " + livre.getIsbn() + " non trouvé");
        Livre livreToUpdate = livreRepository.findById(livre.getIsbn()).get();
        livreToUpdate.setTitre(livre.getTitre());
        livreToUpdate.setPrixHT(livre.getPrixHT());
        livreToUpdate.setAnnee(livre.getAnnee());
        livreRepository.save(livreToUpdate);
        return livreToUpdate;
    }

    public void deleteLivre(String isbn) {
        if(!livreRepository.existsById(isbn)) throw new NoDataFoundException("Livre avec l'isbn " + isbn + " non trouvé");
        livreRepository.deleteById(isbn);
    }
}
