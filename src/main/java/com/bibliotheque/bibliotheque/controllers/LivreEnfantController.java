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

import com.bibliotheque.bibliotheque.dtos.LivreEnfantDTO;
import com.bibliotheque.bibliotheque.entities.LivreEnfant;
import com.bibliotheque.bibliotheque.services.LivreEnfantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "LivreEnfant Controller", description = "Permet de gérer les livres enfants")
@SecurityRequirement(name = "bearerAuth")
public class LivreEnfantController implements HealthIndicator {
    @Autowired
    private LivreEnfantService livreEnfantService;

    @Operation(summary = "Créer un livre enfant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livre créé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @PostMapping("/admin/livres-enfants")
    public ResponseEntity<LivreEnfant> createLivre(@RequestBody LivreEnfantDTO livre) {
        return new ResponseEntity<>(livreEnfantService.createLivre(livre), HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer tous les livres enfants")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des livres enfants"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Livre enfant non trouvé")
    })
    @GetMapping("/livres-enfants")
    public ResponseEntity<List<LivreEnfant>> getAllLivres() {
        return new ResponseEntity<>(livreEnfantService.getAllLivres(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un livre enfant par son isbn")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livre enfant trouvé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Livre enfant non trouvé")
    })
    @Parameter(name = "isbn", description = "Isbn du livre enfant", required = true)
    @GetMapping("/livres-enfants/{isbn}")
    public ResponseEntity<LivreEnfant> getLivreByIsbn(@PathVariable("isbn") String isbn) {
        return new ResponseEntity<>(livreEnfantService.getLivreByIsbn(isbn), HttpStatus.OK);
    }

    @Operation(summary = "Mettre à jour un livre enfant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livre enfant mis à jour"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Livre enfant non trouvé")
    })
    @Parameter(name = "isbn", description = "Isbn du livre enfant", required = true)
    @PutMapping("/admin/livres-enfants")
    public ResponseEntity<LivreEnfant> updateLivre(@RequestBody LivreEnfantDTO livre) {
        return new ResponseEntity<>(livreEnfantService.updateLivre(livre), HttpStatus.OK);
    }

    @Operation(summary = "Supprimer un livre enfant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livre enfant supprimé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Livre enfant non trouvé")
    })
    @DeleteMapping("/admin/livres-enfants/{isbn}")
    @Parameter(name = "isbn", description = "Isbn du livre enfant", required = true)
    public ResponseEntity<Void> deleteLivre(@PathVariable("isbn") String isbn) {
        livreEnfantService.deleteLivre(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Health health() {
        return livreEnfantService != null ? Health.up().build() : Health.down().build();
    }
}
