package com.bibliotheque.bibliotheque.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients_logs")
@Getter
@Setter
@Tag(name = "ClientLogs", description = "Informations d'un client")
public class ClientLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "ID du client", example = "1")
    private int id;

    @Column(name = "client_id")
    @Schema(description = "ID du client", example = "1")
    private int client;

    @Column(name = "ip_address")
    @Schema(description = "Adresse IP du client", example = "127.0.0.1")
    private String ipAddress;

    @Column(name = "status")
    @Schema(description = "Statut de la connexion du client", example = "SUCCESS")
    private String status;

    public ClientLogs() {
    }

    public ClientLogs(int client, String ipAddress, String status) {
        this.client = client;
        this.ipAddress = ipAddress;
        this.status = status;
    }
}
