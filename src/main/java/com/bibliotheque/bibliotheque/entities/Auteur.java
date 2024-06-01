package com.bibliotheque.bibliotheque.entities;

import com.bibliotheque.bibliotheque.dtos.AuteurDTO;

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
@Table(name = "auteurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Auteur", description = "Informations d'un auteur")
public class Auteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de l'auteur", example = "1")
    private int id;

    @Schema(description = "Nom de l'auteur", example = "Doe")
    private String nom;

    @Schema(description = "Prénom de l'auteur", example = "John")
    private String prenom;

    @Schema(description = "Pseudo de l'auteur", example = "JohnDoe")
    private String pseudo;

    public Auteur(AuteurDTO auteur) {
        this.nom = auteur.getNom();
        this.prenom = auteur.getPrenom();
        this.pseudo = auteur.getPseudo();
    }
}
