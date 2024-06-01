package com.bibliotheque.bibliotheque.entities;

import com.bibliotheque.bibliotheque.dtos.ProfilDTO;
import com.bibliotheque.bibliotheque.enums.Genre;
import com.bibliotheque.bibliotheque.enums.Langage;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profils")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Profil", description = "Informations d'un profil")
public class Profil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID du profil", example = "1")
    private int id;
    
    @Schema(description = "Langage préféré", example = "FR")
    private Langage langage;

    @Schema(description = "Genre de livre préféré", example = "ROMAN")
    private Genre preference;

    public Profil(ProfilDTO profil) {
        this.langage = profil.getLangage();
        this.preference = profil.getPreference();
    }
}
