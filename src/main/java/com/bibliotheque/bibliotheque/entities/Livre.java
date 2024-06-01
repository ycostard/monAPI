package com.bibliotheque.bibliotheque.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.bibliotheque.bibliotheque.dtos.LivreDTO;
import com.bibliotheque.bibliotheque.dtos.LivreEnfantDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "livres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Livre", description = "Informations d'un livre")
public class Livre {
    
    @Id
    @Schema(description = "ISBN du livre", example = "FR2023FT")
    private String isbn;

    @Schema(description = "Titre du livre", example = "Le Seigneur des Anneaux")
    private String titre;

    @ManyToOne
    @JoinColumn(name = "idAuteur")
    @Schema(description = "Auteur du livre")
    private Auteur auteur;

    @Schema(description = "Prix HT du livre", example = "15.99")
    private Float prixHT;

    @Schema(description = "Année de parution du livre", example = "1954")
    private int annee;

    public Livre(LivreDTO livreDTO){
        this.isbn = livreDTO.getIsbn();
        this.titre = livreDTO.getTitre();
        this.prixHT = livreDTO.getPrixHT();
        this.annee = livreDTO.getAnnee();
    }

    public Livre(LivreEnfantDTO livre) {
        this.isbn = livre.getIsbn();
        this.titre = livre.getTitre();
        this.prixHT = livre.getPrixHT();
        this.annee = livre.getAnnee();
    }
}
