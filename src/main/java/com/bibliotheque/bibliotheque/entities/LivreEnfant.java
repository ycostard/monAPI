package com.bibliotheque.bibliotheque.entities;

import com.bibliotheque.bibliotheque.dtos.LivreEnfantDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livres_enfants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "LivreEnfant", description = "Informations d'un livre enfant")
public class LivreEnfant extends Livre {
    
    @Schema(description = "Age recommandé pour lire le livre", example = "8")
    private int age;

    public LivreEnfant(LivreEnfantDTO livre) {
        super(livre);
        this.age = livre.getAge();
    }
}
