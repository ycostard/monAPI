package com.bibliotheque.bibliotheque.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliotheque.bibliotheque.entities.ClientLogs;
import com.bibliotheque.bibliotheque.services.ClientLogsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("admin/clients-logs")
@Tag(name = "Admin ClientLogs Controller", description = "Permet de gérer les logs des clients")
@SecurityRequirement(name = "bearerAuth")
public class AdminClientLogsController implements HealthIndicator {
    
    @Autowired
    private ClientLogsService clientLogsService;

    @Operation(summary = "Récupérer tous les logs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des logs"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @GetMapping("")
    public ResponseEntity<List<ClientLogs>> getAllLogs() {
        return new ResponseEntity<>(clientLogsService.getAllLogs(), HttpStatus.OK);
    }

    @Override
    public Health health() {
        return clientLogsService != null ? Health.up().build() : Health.down().build();
    }
    
}
