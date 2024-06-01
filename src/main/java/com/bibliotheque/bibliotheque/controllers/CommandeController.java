package com.bibliotheque.bibliotheque.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliotheque.bibliotheque.dtos.CommandeDTO;
import com.bibliotheque.bibliotheque.entities.Commande;
import com.bibliotheque.bibliotheque.services.CommandeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/commandes")
@Tag(name = "Commande Controller", description = "Permet de gérer les commandes")
@SecurityRequirement(name = "bearerAuth")
public class CommandeController implements HealthIndicator {

    @Autowired
    private CommandeService commandeService;

    @Operation(summary = "Créer une commande")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Commande créée"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @PostMapping("")
    public ResponseEntity<Commande> createCommande(Principal principal, @RequestBody CommandeDTO commande) {
        return new ResponseEntity<>(commandeService.createCommande(principal.getName(), commande), HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer toutes ses commandes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des commandes"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @GetMapping("")
    public ResponseEntity<List<Commande>> getAllCommandes(Principal principal) {
        return new ResponseEntity<>(commandeService.getAllCommandesByUsername(principal.getName()), HttpStatus.OK);
    }

    @Override
    public Health health() {
        return commandeService != null ? Health.up().build() : Health.down().build();
    }


}
