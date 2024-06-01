package com.bibliotheque.bibliotheque.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bibliotheque.bibliotheque.dtos.CommandeDTO;
import com.bibliotheque.bibliotheque.entities.Client;
import com.bibliotheque.bibliotheque.entities.Commande;
import com.bibliotheque.bibliotheque.enums.CommandeStatus;
import com.bibliotheque.bibliotheque.services.CommandeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin/commandes")
@Tag(name = "Admin Commande Controller", description = "Permet de gérer les commandes")
@SecurityRequirement(name = "bearerAuth")
public class AdminCommandeController implements HealthIndicator {
    @Autowired
    private CommandeService commandeService;

    @Operation(summary = "Créer une commande pour un client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Commande créée"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @PostMapping("")
    public ResponseEntity<Commande> createCommande(@RequestBody CommandeDTO commande) {
        return new ResponseEntity<>(commandeService.createCommandeByAdmin(commande), HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer toutes les commandes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des commandes"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @GetMapping("")
    public ResponseEntity<List<Commande>> getAllCommandes() {
        return new ResponseEntity<>(commandeService.getAllCommandes(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer une commande par son id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Commande trouvée"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    })
    @Parameter(name = "id", description = "ID de la commande", example = "1", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable("id") int id) {
        return new ResponseEntity<>(commandeService.getCommandeById(id), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer les commandes d'un client, par date ou/et par status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Commandes trouvées"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Commandes non trouvées")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Commande>> getCommandeByDateOrStatusOrClient(@RequestParam("date") Date date,
            @RequestParam("status") CommandeStatus status, @RequestParam("client") Client client) {
        return new ResponseEntity<>(commandeService.getCommandeByDateOrStatusOrClient(date, status, client), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer le chiffre d'affaire")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chiffre d'affaire trouvé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping("/ca")
    public ResponseEntity<Float> getChiffreAffaire() {
        return new ResponseEntity<>(commandeService.getChiffreAffaire(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer le chiffre d'affaire prévisionnel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chiffre d'affaire prévisionnel trouvé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping("/ca-previsionnel")
    public ResponseEntity<Float> getChiffreAffairePrevisionnel() {
        return new ResponseEntity<>(commandeService.getChiffreAffairePrevisionnel(), HttpStatus.OK);
    }

    @Operation(summary = "Mettre à jour une commande")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Commande mise à jour"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    })
    @PutMapping("/{id}")
    @Parameter(name = "id", description = "ID de la commande", example = "1", required = true)
    public ResponseEntity<CommandeDTO> updateCommande(@PathVariable("id") int id, @RequestBody CommandeDTO commande) {
        return new ResponseEntity<>(commandeService.updateCommande(id, commande), HttpStatus.OK);
    }

    @Override
    public Health health() {
        return commandeService != null ? Health.up().build() : Health.down().build();
    }
}
