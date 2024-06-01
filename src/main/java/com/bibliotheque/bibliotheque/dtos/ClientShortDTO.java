package com.bibliotheque.bibliotheque.dtos;

import com.bibliotheque.bibliotheque.entities.Client;
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
@Tag(name = "ClientShortDTO", description = "Informations d'un client")
public class ClientShortDTO {
    
    @JsonProperty("username")
    @Schema(description = "Nom d'utilisateur du client", example = "JohnDoe")
    private String username;

    @JsonProperty("nom")
    @Schema(description = "Nom du client", example = "Doe")
    private String nom;

    @JsonProperty("prenom")
    @Schema(description = "Prénom du client", example = "John")
    private String prenom;

    @JsonProperty("adresse")
    @Schema(description = "Adresse du client", example = "1 rue de la Paix")
    private String adresse;

    @JsonProperty("profil")
    @Schema(description = "Profil du client")
    private ProfilDTO profil;

    public ClientShortDTO(Client client) {
        this.username = client.getUsername();
        this.nom = client.getNom();
        this.prenom = client.getPrenom();
        this.adresse = client.getAdresse();
        this.profil = new ProfilDTO(client.getProfil());
    }

    public ClientShortDTO(ClientDTO client) {
        this.username = client.getUsername();
        this.nom = client.getNom();
        this.prenom = client.getPrenom();
        this.adresse = client.getAdresse();
        this.profil = client.getProfil();
    }
}
