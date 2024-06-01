package com.bibliotheque.bibliotheque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bibliotheque.bibliotheque.dtos.AuteurDTO;
import com.bibliotheque.bibliotheque.entities.Auteur;
import com.bibliotheque.bibliotheque.exceptions.NoDataFoundException;
import com.bibliotheque.bibliotheque.repositories.AuteurRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AuteurServiceTest {

    private AuteurService auteurService;

    @Mock
    private AuteurRepository auteurRepository;

    @BeforeEach
    void setup() {
        auteurService = new AuteurService(auteurRepository);
    }


    @Test
    void getAllAuteursTestEmpty() throws Exception {
        assertThrows(NoDataFoundException.class, () -> auteurService.getAllAuteurs());
    }

    @Test
    void getAllAuteursTestNotEmpty() throws Exception {
        List<Auteur> auteurs = new ArrayList<>();

        auteurs.add(new Auteur(1,"Costard", "Yohann", "yoyo"));
        auteurs.add(new Auteur(2,"Costard1", "Yohann1", "yoyo1"));
        auteurs.add(new Auteur(3, "Costard2", "Yohann2", "yoyo2"));

        when(auteurRepository.findAll()).thenReturn(auteurs);

        assertEquals(3, auteurService.getAllAuteurs().size());
    }

    @Test
    void getAuteurByIdTestEmpty() throws Exception {
        assertThrows(NoDataFoundException.class, () -> auteurService.getAuteurById(1));
    }

    @Test
    void getAuteurByIdTestNotEmpty() throws Exception {
        when(auteurRepository.existsById(1)).thenReturn(true);
        when(auteurRepository.findById(1)).thenReturn(Optional.of(new Auteur(1,"Costard", "Yohann", "yoyo")));

        assertEquals("Costard", auteurService.getAuteurById(1).getNom());
        assertEquals("Yohann", auteurService.getAuteurById(1).getPrenom());
        assertEquals("yoyo", auteurService.getAuteurById(1).getPseudo());
    }

    @Test
    void getRealAuteurByIdTestEmpty() throws Exception {
        assertThrows(NoDataFoundException.class, () -> auteurService.getRealAuteurById(1));
    }

    @Test
    @Transactional
    void getRealAuteurByIdTestNotEmpty() throws Exception {
        Auteur auteur = new Auteur(1,"Costard", "Yohann", "yoyo");

        when(auteurRepository.existsById(1)).thenReturn(true);
        when(auteurRepository.findById(1)).thenReturn(Optional.of(auteur));

        assertEquals(1, auteurService.getRealAuteurById(1).getId());
        assertEquals("Costard", auteurService.getRealAuteurById(1).getNom());
        assertEquals("Yohann", auteurService.getRealAuteurById(1).getPrenom());
        assertEquals("yoyo", auteurService.getRealAuteurById(1).getPseudo());
    }

    @Test
    void deleteAuteurByIdTest() throws Exception {
        when(auteurRepository.existsById(1)).thenReturn(true);
        auteurService.deleteAuteur(1);

        verify(auteurRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteAuteurByIdButAuteurNotExistTest() throws Exception {
        assertThrows(NoDataFoundException.class, () -> auteurService.deleteAuteur(1));
    }

    @Test
    void updateAuteurTest() throws Exception {
        AuteurDTO auteurDTO = new AuteurDTO("Costard","Yohann", "yoyo");

        Auteur auteur = new Auteur(1,"Oui","Non", "Test");

        when(auteurRepository.existsById(1)).thenReturn(true);
        when(auteurRepository.findById(1)).thenReturn(Optional.of(auteur));
        when(auteurRepository.save(Mockito.any())).thenReturn(auteur);

        assertEquals(auteurDTO.getNom(), auteurService.updateAuteur(1,auteurDTO).getNom());
        assertEquals(auteurDTO.getPrenom(), auteurService.updateAuteur(1,auteurDTO).getPrenom());
        assertEquals(auteurDTO.getPseudo(), auteurService.updateAuteur(1,auteurDTO).getPseudo());
    }


}