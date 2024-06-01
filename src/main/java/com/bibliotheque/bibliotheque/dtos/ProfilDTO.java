package com.bibliotheque.bibliotheque.dtos;

import com.bibliotheque.bibliotheque.entities.Profil;
import com.bibliotheque.bibliotheque.enums.Genre;
import com.bibliotheque.bibliotheque.enums.Langage;
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
@Tag(name = "ProfilDTO", description = "Informations d'un profil")
public class ProfilDTO {
    
    @JsonProperty("langage")
    @Schema(name = "Langage", description = "Langage préféré", example = "FR")
    private Langage langage;

    @JsonProperty("preference")
    @Schema(name = "Preference", description = "Genre de livre préféré", example = "HORROR")
    private Genre preference;

    public ProfilDTO(Profil profil){
        this.langage = profil.getLangage();
        this.preference = profil.getPreference();
    }
}