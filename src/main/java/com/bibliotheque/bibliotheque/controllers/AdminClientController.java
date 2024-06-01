package com.bibliotheque.bibliotheque.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliotheque.bibliotheque.dtos.ClientDTO;
import com.bibliotheque.bibliotheque.dtos.ClientShortDTO;
import com.bibliotheque.bibliotheque.services.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin/clients")
@Tag(name = " Admin Client Controller", description = "Permet de gérer les clients")
@SecurityRequirement(name = "bearerAuth")
public class AdminClientController implements HealthIndicator {
     @Autowired
    private ClientService clientService;    

    @Operation(summary = "Récupérer tous les clients")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des clients"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @GetMapping("")
    public ResponseEntity<List<ClientShortDTO>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un client par son id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client trouvé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @Parameter(name = "id", description = "ID du client", example = "1", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<ClientShortDTO> getClientById(@PathVariable("id") int id) {
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @Operation(summary = "Mettre à jour un client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client mis à jour"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @Parameter(name = "id", description = "ID du client", example = "1", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<ClientShortDTO> updateClient(@PathVariable("id") int id, @RequestBody ClientDTO client) {
        return new ResponseEntity<>(clientService.updateClient(id, client), HttpStatus.OK);
    }

    @Override
    public Health health() {
        return clientService != null ? Health.up().build() : Health.down().build();
    }
}
