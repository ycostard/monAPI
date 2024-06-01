package com.bibliotheque.bibliotheque.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bibliotheque.bibliotheque.dtos.AuteurDTO;
import com.bibliotheque.bibliotheque.services.AuteurService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Auteur Controller", description = "Permet de gérer les auteurs")
@SecurityRequirement(name = "bearerAuth")
public class AuteurController implements HealthIndicator {

    @Autowired
    private AuteurService auteurService;

    @Operation(summary = "Créer un auteur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Auteur créé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @PostMapping("/admin/auteurs")
    public ResponseEntity<AuteurDTO> createAuteur(@RequestBody AuteurDTO auteur) {
        return new ResponseEntity<>(auteurService.createAuteur(auteur), HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer tous les auteurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des auteurs"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @GetMapping("/auteurs")
    public ResponseEntity<List<AuteurDTO>> getAllAuteurs() {
        return new ResponseEntity<>(auteurService.getAllAuteurs(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un auteur par son id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Auteur trouvé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Auteur non trouvé")
    })
    @Parameter(name = "id", description = "ID de l'auteur", required = true)
    @GetMapping("/auteurs/{id}")
    public ResponseEntity<AuteurDTO> getAutheurById(@PathVariable("id") int id) {
        return new ResponseEntity<>(auteurService.getAuteurById(id), HttpStatus.OK);
    }

    @Operation(summary = "Mettre à jour un auteur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Auteur mis à jour"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Auteur non trouvé")
    })
    @PutMapping("/admin/auteurs/{id}")
    @Parameter(name = "id", description = "ID de l'auteur", required = true)
    public ResponseEntity<AuteurDTO> updateAuteur(@PathVariable("id") int id, @RequestBody AuteurDTO auteur) {
        return new ResponseEntity<>(auteurService.updateAuteur(id, auteur), HttpStatus.OK);
    }

    @Operation(summary = "Supprimer un auteur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Auteur supprimé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Auteur non trouvé")
    })
    @Parameter(name = "id", description = "ID de l'auteur", required = true)
    @DeleteMapping("/admin/auteurs/{id}")
    public ResponseEntity<Void> deleteAuteur(@PathVariable("id") int id) {
        auteurService.deleteAuteur(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Health health() {
        return auteurService != null ? Health.up().build() : Health.down().build();
    }
}
