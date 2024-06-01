package com.bibliotheque.bibliotheque.dtos;

import java.util.Date;
import java.util.List;

import com.bibliotheque.bibliotheque.enums.CommandeStatus;
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
@Tag(name = "CommandeDTO", description = "Informations d'une commande")
public class CommandeDTO {
    
    @JsonProperty("client")
    @Schema(description = "Client de la commande")
    private Integer client;

    @JsonProperty("livres")
    @Schema(description = "Livres de la commande", example = "[\"FR2023FT\", \"FR2023FB\"]")
    private List<String> livres;

    @JsonProperty("date")
    @Schema(description = "Date de la commande", example = "2021-08-01")
    private Date date;

    @JsonProperty("status")
    @Schema(description = "Status de la commande", example = "Expédiée")
    private CommandeStatus status;
}
