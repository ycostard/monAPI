package com.bibliotheque.bibliotheque.dtos;

import com.bibliotheque.bibliotheque.entities.Auteur;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "AuteurDTO", description = "Informations d'un auteur")
public class AuteurDTO {
    
    @JsonProperty("nom")
    @Schema(description = "Nom de l'auteur", example = "Doe")
    private String nom;

    @JsonProperty("prenom")
    @Schema(description = "Prénom de l'auteur", example = "John")
    private String prenom;

    @JsonProperty("pseudo")
    @Schema(description = "Pseudo de l'auteur", example = "JohnDoe")
    private String pseudo;

    public AuteurDTO(Auteur auteur) {
        this.nom = auteur.getNom();
        this.prenom = auteur.getPrenom();
        this.pseudo = auteur.getPseudo();
    }
}