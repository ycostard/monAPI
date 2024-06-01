package com.bibliotheque.bibliotheque.dtos;

import com.bibliotheque.bibliotheque.entities.Livre;
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
@Tag(name = "LivreDTO", description = "Informations d'un livre")
public class LivreDTO {

    @JsonProperty("isbn")
    @Schema(description = "ISBN du livre", example = "FR2023FT")
    private String isbn;

    @JsonProperty("titre")
    @Schema(description = "Titre du livre", example = "Le Seigneur des Anneaux")
    private String titre;

    @JsonProperty("auteur")
    @Schema(description = "Auteur du livre")
    private int idAuteur;

    @JsonProperty("prixHT")
    @Schema(description = "Prix HT du livre", example = "15.99")
    private Float prixHT;

    @JsonProperty("annee")
    @Schema(description = "Année de parution du livre", example = "1954")
    private int annee;

    public LivreDTO(Livre livre){
        this.isbn = livre.getIsbn();
        this.titre = livre.getTitre();
        this.prixHT = livre.getPrixHT();
        this.annee = livre.getAnnee();
    }
}
