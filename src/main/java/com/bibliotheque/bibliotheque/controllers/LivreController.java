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

import com.bibliotheque.bibliotheque.dtos.LivreDTO;
import com.bibliotheque.bibliotheque.entities.Livre;
import com.bibliotheque.bibliotheque.services.LivreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Livre Controller", description = "Permet de gérer les livres")
@SecurityRequirement(name = "bearerAuth")
public class LivreController implements HealthIndicator {
    
    @Autowired
    private LivreService livreService;


    @Operation(summary = "Créer un livre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livre créé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @PostMapping("/admin/livres")
    public ResponseEntity<Livre> createLivre(@RequestBody LivreDTO livre) {
        return new ResponseEntity<>(livreService.createLivre(livre), HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer tous les livres")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des livres"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @GetMapping("/livres")
    public ResponseEntity<List<Livre>> getAllLivres() {
        return new ResponseEntity<>(livreService.getAllLivres(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un livre par son id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livre trouvé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @Parameter(name = "isbn", description = "ISBN du livre", required = true)
    @GetMapping("/livres/{isbn}")
    public ResponseEntity<Livre> getLivreByIsbn(@PathVariable("isbn") String isbn) {
        return new ResponseEntity<>(livreService.getLivreByIsbn(isbn), HttpStatus.OK);
    }

    @Operation(summary = "Mettre à jour un livre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livre mis à jour"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @PutMapping("/admin/livres")
    public ResponseEntity<Livre> updateLivre(@RequestBody LivreDTO livre) {
        return new ResponseEntity<>(livreService.updateLivre(livre), HttpStatus.OK);
    }

    @Operation(summary = "Supprimer un livre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livre supprimé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @DeleteMapping("/admin/livres/{isbn}")
    @Parameter(name = "isbn", description = "ISBN du livre", required = true)
    public ResponseEntity<Void> deleteLivre(@PathVariable("isbn") String isbn) {
        livreService.deleteLivre(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Health health() {
        return livreService != null ? Health.up().build() : Health.down().build();
    }
}
