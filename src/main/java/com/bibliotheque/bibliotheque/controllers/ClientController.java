package com.bibliotheque.bibliotheque.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bibliotheque.bibliotheque.dtos.ClientDTO;
import com.bibliotheque.bibliotheque.dtos.ClientShortDTO;
import com.bibliotheque.bibliotheque.services.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;



@RestController
@Tag(name = "Client Controller", description = "Permet de gérer les clients")
public class ClientController implements HealthIndicator {

    @Autowired
    private ClientService clientService;    

    @Operation(summary = "Créer un client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Client créé"),
        @ApiResponse(responseCode = "409", description = "Client déjà existant"),
    })
    @PostMapping("/clients/signup")
    public ResponseEntity<String> createClient(@RequestBody @Valid ClientDTO clientDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(clientService.signup(clientDTO), HttpStatus.CREATED);
        
    }

    @Operation(summary = "Se connecter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client connecté"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "401", description = "Compte bloqué"),
    })
    @PostMapping("/clients/signin")
    public ResponseEntity<String> connectClient(@RequestBody ClientDTO clientDTO) {
        try {
            String token = clientService.signin(clientDTO.getUsername(), clientDTO.getPassword());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadCredentialsException | LockedException e) {
            return new ResponseEntity<>(e.getMessage().toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Récupérer ses informations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client trouvé"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/clients")
    public ResponseEntity<ClientShortDTO> getClient(Principal principal) {
        return new ResponseEntity<>(new ClientShortDTO(clientService.getClientByUsername(principal.getName())), HttpStatus.OK);
    }

    @Operation(summary = "Mettre à jour ses informations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client mis à jour"),
        @ApiResponse(responseCode = "401", description = "Non autorisé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/clients")
    public ResponseEntity<ClientShortDTO> updateClient(Principal principal, @RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.updateClientByUsername(principal.getName(), clientDTO), HttpStatus.OK);
    }

    @Override
    public Health health() {
        return clientService != null ? Health.up().build() : Health.down().build();
    }
}
