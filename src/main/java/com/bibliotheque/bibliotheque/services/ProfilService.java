package com.bibliotheque.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.bibliotheque.dtos.ProfilDTO;
import com.bibliotheque.bibliotheque.entities.Profil;
import com.bibliotheque.bibliotheque.repositories.ProfilRepository;

@Service
public class ProfilService {

    @Autowired
    private ProfilRepository profilRepository;

    public ProfilDTO createProfil(ProfilDTO profil) {
        profilRepository.save(new Profil(profil));
        return profil;
    }
}
