package com.bibliotheque.bibliotheque.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

import com.bibliotheque.bibliotheque.enums.CommandeStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Entity
@Table(name = "commandes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Commande", description = "Informations d'une commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la commande", example = "1")
    private int id;

    @ManyToOne
    @Schema(description = "Client de la commande")
    private Client client;

    @ManyToMany
    @Schema(description = "Livres de la commande", example = "[\"FR2023FT\", \"FR2023FB\"]")
    private List<Livre> livres;

    @Schema(description = "Date de la commande", example = "2021-08-01")
    private Date date;

    @Schema(description = "Status de la commande", example = "Expédiée")
    private CommandeStatus status;

    @Schema(description = "Prix HT de la commande", example = "20.0")
    private Float prixHT;

    @Schema(description = "Prix TTC de la commande", example = "24.0")
    @Transient
    private Float prixTTC;
}
