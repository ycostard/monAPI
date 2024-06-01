package com.bibliotheque.bibliotheque.entities;

import java.util.Collection;
import java.util.List;

import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bibliotheque.bibliotheque.dtos.ClientDTO;
import com.bibliotheque.bibliotheque.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Client", description = "Informations d'un client")
public class Client implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID du client", example = "1")
    private int id;

    @Unique
    @Schema(description = "Nom d'utilisateur du client", example = "JohnDoe")
    private String username;

    @JsonIgnore
    @Schema(description = "Mot de passe du client", example = "Password814!")
    private String password;

    @Schema(description = "Nom du client", example = "Doe")
    private String nom;

    @Schema(description = "Prénom du client", example = "John")
    private String prenom;

    @Schema(description = "Adresse du client", example = "1 rue de la Paix")
    private String adresse;

    @OneToOne
    @JoinColumn(name = "idProfil")
    @Schema(description = "Profil du client")
    private Profil profil;

    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @Schema(description = "Rôles du client")
    private List<Role> role;

    @Column(name = "isNonLocked")
    @Schema(description = "Statut du client")
    private boolean isNonLocked;

    public Client(ClientDTO client, Profil profil) {
        this.username = client.getUsername();
        this.password = client.getPassword();
        this.nom = client.getNom();
        this.prenom = client.getPrenom();
        this.adresse = client.getAdresse();
        this.profil = profil;
    }

    public Client(ClientDTO client) {
        this.nom = client.getNom();
        this.prenom = client.getPrenom();
        this.adresse = client.getAdresse();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
