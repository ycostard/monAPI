package com.bibliotheque.bibliotheque.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bibliotheque.bibliotheque.configurations.JWTTokenProvider;
import com.bibliotheque.bibliotheque.dtos.ClientDTO;
import com.bibliotheque.bibliotheque.dtos.ClientShortDTO;
import com.bibliotheque.bibliotheque.entities.Client;
import com.bibliotheque.bibliotheque.entities.Profil;
import com.bibliotheque.bibliotheque.enums.Role;
import com.bibliotheque.bibliotheque.exceptions.ClientAlreadyExistException;
import com.bibliotheque.bibliotheque.exceptions.NoDataFoundException;
import com.bibliotheque.bibliotheque.repositories.ClientRepository;
import com.bibliotheque.bibliotheque.repositories.ProfilRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<ClientShortDTO> getAllClients() {
        List<ClientShortDTO> clients = new ArrayList<>();
        clientRepository.findAll().forEach(client -> clients.add(new ClientShortDTO(client)));
        if(clients.isEmpty()) throw new NoDataFoundException("Aucun Client trouvé");
        return clients;
    }

    public ClientShortDTO getClientById(int id) {
        if(!clientRepository.existsById(id)) throw new NoDataFoundException("Client avec l'id " + id + " non trouvé");
        return new ClientShortDTO(clientRepository.findById(id).get());
    }

    public Client getRealClientById(int id) {
        if(!clientRepository.existsById(id)) throw new NoDataFoundException("Client avec l'id " + id + " non trouvé");
        return clientRepository.findById(id).get();
    }

    public Client getClientByUsername(String username) {
        return clientRepository.findByUsername(username).orElseThrow(() -> new NoDataFoundException("Client avec le username " + username + " non trouvé"));
    }
    
    public ClientShortDTO updateClient(int id, ClientDTO client) {
        if(!clientRepository.existsById(id)) throw new NoDataFoundException("Client avec l'id " + id + " non trouvé");
        Client clientToUpdate = clientRepository.findById(id).get();
        clientToUpdate.setNom(client.getNom());
        clientToUpdate.setPrenom(client.getPrenom());
        clientToUpdate.setAdresse(client.getAdresse());
        clientToUpdate.setProfil(profilRepository.save(new Profil(client.getProfil())));
        clientRepository.save(clientToUpdate);
        return new ClientShortDTO(client);
    }

    public ClientShortDTO updateClientByUsername(String name, ClientDTO client) {
        if(!clientRepository.existsByUsername(name)) throw new NoDataFoundException("Client avec l'username " + name + " non trouvé");
        Client clientToUpdate = clientRepository.findByUsername(name).get();
        clientToUpdate.setNom(client.getNom());
        clientToUpdate.setPrenom(client.getPrenom());
        clientToUpdate.setAdresse(client.getAdresse());
        clientToUpdate.setProfil(profilRepository.save(new Profil(client.getProfil())));
        clientRepository.save(clientToUpdate);
        return new ClientShortDTO(client);
    }

    public String signup(ClientDTO clientDTO) {
        if(clientRepository.existsByUsername(clientDTO.getUsername())) throw new ClientAlreadyExistException("Client avec le username " + clientDTO.getUsername() + " existe déjà");
        Client client = new Client(clientDTO, profilRepository.save(new Profil(clientDTO.getProfil())));
        
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_CLIENT);
        client.setRole(roles);
        client.setNonLocked(true);
        clientRepository.save(client);

        //Role is collection empty
        return jwtTokenProvider.createToken(client);
    }

    public String signin(String username, String password) throws BadCredentialsException, LockedException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(clientRepository.findByUsername(username).get());
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateLockedClient(Client client, boolean isLocked) {
        client.setNonLocked(isLocked);
        clientRepository.save(client);
    }
}
