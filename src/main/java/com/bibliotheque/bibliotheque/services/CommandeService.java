package com.bibliotheque.bibliotheque.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.bibliotheque.dtos.CommandeDTO;
import com.bibliotheque.bibliotheque.entities.Client;
import com.bibliotheque.bibliotheque.entities.Commande;
import com.bibliotheque.bibliotheque.entities.Livre;
import com.bibliotheque.bibliotheque.enums.CommandeStatus;
import com.bibliotheque.bibliotheque.enums.Langage;
import com.bibliotheque.bibliotheque.exceptions.NoDataFoundException;
import com.bibliotheque.bibliotheque.repositories.CommandeRepository;

@Service
public class CommandeService {
    
    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private LivreService livreService;

    @Autowired
    private ClientService clientService;

    public Commande createCommande(String username, CommandeDTO commande) {
        Client client = clientService.getClientByUsername(username);

        List<Livre> livresToSave = new ArrayList<>();
        float prix = 0;

        for(String isbn : commande.getLivres()){
            Livre livre = livreService.getLivreByIsbn(isbn);
            prix += livre.getPrixHT();
            livresToSave.add(livre);
        }

        Commande commandeToSave = new Commande();
        commandeToSave.setClient(client);
        commandeToSave.setLivres(livresToSave);        
        commandeToSave.setPrixHT(prix);
        commandeToSave.setStatus(CommandeStatus.Préparation);
        commandeToSave.setDate(new Date());
        commandeRepository.save(commandeToSave);
        
        return commandeToSave;
    }

    public Commande createCommandeByAdmin(CommandeDTO commande) {
        Client client = clientService.getRealClientById(commande.getClient());

        List<Livre> livresToSave = new ArrayList<>();
        float prix = 0;

        for(String isbn : commande.getLivres()){
            Livre livre = livreService.getLivreByIsbn(isbn);
            prix += livre.getPrixHT();
            livresToSave.add(livre);
        }

        Commande commandeToSave = new Commande();
        commandeToSave.setClient(client);
        commandeToSave.setLivres(livresToSave);        
        commandeToSave.setPrixHT(prix);
        commandeToSave.setStatus(CommandeStatus.Préparation);
        commandeToSave.setDate(new Date());
        commandeRepository.save(commandeToSave);
        
        return commandeToSave;
    }

    public List<Commande> getAllCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        if(commandes.isEmpty()) throw new NoDataFoundException("Aucune Commande trouvée");
        return commandes;
    }

    public List<Commande> getAllCommandesByUsername(String username) {
        Client client = clientService.getClientByUsername(username);
        List<Commande> commandes = commandeRepository.findByClient(client);
        if(commandes.isEmpty()) throw new NoDataFoundException("Aucune Commande trouvée");

        float prixHT = 0;
        float tva = 0;
        
        if(client.getProfil().getLangage() == Langage.FR)
            tva = (float) 1.05;
        else if (client.getProfil().getLangage() == Langage.EN)
            tva = (float) 1.15;
        else 
            tva = (float) 1.10;
        
        for(Commande commande : commandes){
            for(Livre livre : commande.getLivres()){
                prixHT += livre.getPrixHT();
            }
            commande.setPrixTTC(prixHT * tva);
        }
        return commandes;
    }

    public Commande getCommandeById(int id) {
        if(!commandeRepository.existsById(id)) throw new NoDataFoundException("Commande avec l'id " + id + " non trouvée");
        return commandeRepository.findById(id).get();
    }

    public CommandeDTO updateCommande(int id, CommandeDTO commande) {
        if(!commandeRepository.existsById(id)) throw new NoDataFoundException("Commande avec l'id " + id + " non trouvée");
        Commande commandeToUpdate = commandeRepository.findById(id).get();

        List<Livre> livresToSave = new ArrayList<>();
        for(String isbn : commande.getLivres()){
            livresToSave.add(livreService.getLivreByIsbn(isbn));
        }
        commandeToUpdate.setLivres(livresToSave);
        commandeToUpdate.setDate(commande.getDate());
        commandeRepository.save(commandeToUpdate);
        
        return commande;
    }

    public List<Commande> getCommandeByDateOrStatusOrClient(Date date, CommandeStatus status, Client client) {
        List<Commande> commandes = new ArrayList<>();

        if(date != null && status != null && client != null)
            commandeRepository.findByDateAndStatusAndClient(date, status, client).forEach(commande -> commandes.add(commande));
        else if(date != null && status != null)
            commandeRepository.findByDateAndStatus(date, status).forEach(commande -> commandes.add(commande));
        else if(date != null && client != null)
            commandeRepository.findByDateAndClient(date, client).forEach(commande -> commandes.add(commande));
        else if(status != null && client != null)
            commandeRepository.findByStatusAndClient(status, client).forEach(commande -> commandes.add(commande));
        else if(date != null)
            commandeRepository.findByDate(date).forEach(commande -> commandes.add(commande));
        else if(status != null)
            commandeRepository.findByStatus(status).forEach(commande -> commandes.add(commande));
        else if(client != null)
            commandeRepository.findByClient(client).forEach(commande -> commandes.add(commande));
        if(commandes.isEmpty()) throw new NoDataFoundException("Aucune Commande trouvée");
        return commandes;
    }

    public float getChiffreAffaire(){
        float chiffreAffaire = 0;
        for(Commande commande : commandeRepository.findByStatus(CommandeStatus.Livrée)){
            chiffreAffaire += commande.getPrixHT();
        }
        return chiffreAffaire;
    }

    public float getChiffreAffairePrevisionnel(){
        float chiffreAffaire = 0;
        for(Commande commande : commandeRepository.findAll()){
            chiffreAffaire += commande.getPrixHT();
        }
        return chiffreAffaire;
    }
}
