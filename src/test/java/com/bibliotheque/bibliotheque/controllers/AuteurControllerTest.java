package com.bibliotheque.bibliotheque.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.bibliotheque.bibliotheque.dtos.AuteurDTO;
import com.bibliotheque.bibliotheque.entities.Auteur;
import com.bibliotheque.bibliotheque.repositories.AuteurRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



@SpringBootTest
@AutoConfigureMockMvc
public class AuteurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuteurRepository auteurRepository;

    @Test
    @WithMockUser(username = "userTest", roles = "USER")
    void getAllAuteursTestNotEmpty() throws Exception {
        List<Auteur> auteurs = new ArrayList<>();

        auteurs.add(new Auteur(1,"Costard", "Yohann", "yoyo"));
        auteurs.add(new Auteur(2,"Costard1", "Yohann1", "yoyo1"));
        auteurs.add(new Auteur(3, "Costard2", "Yohann2", "yoyo2"));

        when(auteurRepository.findAll()).thenReturn(auteurs);

        mockMvc.perform(get("/auteurs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(auteurs.size())));
    }

    @Test
    @WithMockUser(username = "userTest", roles = "USER")
    void getAuteurByIdWhereAuteurNotExistTest() throws Exception {
        
        mockMvc.perform(get("/auteurs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "userTest", roles = "USER")
    void getAuteurByIdTestNotEmpty() throws Exception {
        when(auteurRepository.existsById(1)).thenReturn(true);
        when(auteurRepository.findById(1)).thenReturn(java.util.Optional.of(new Auteur(1,"Costard", "Yohann", "yoyo4")));

        mockMvc.perform(get("/auteurs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"nom\":\"Costard\",\"prenom\":\"Yohann\",\"pseudo\":\"yoyo4\"}")));
    }

    @Test
    @WithMockUser(username = "userTest", roles = "USER")
    void getAuteurByNotGoodIdTest() throws Exception {
        mockMvc.perform(get("/auteurs/bb")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "userTest", roles = "ADMIN")
    void createGoodAuteurTest() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        AuteurDTO auteurDTO = new AuteurDTO("Costard","Yohann", "yoyo");

        mockMvc.perform(post("/admin/auteurs").content(ow.writeValueAsString(auteurDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("{\"nom\":\"Costard\",\"prenom\":\"Yohann\",\"pseudo\":\"yoyo\"}")));
    }

    @Test
    @WithMockUser(username = "userTest", roles = "ADMIN")
    void deleteAuteurByIdTest() throws Exception {
        when(auteurRepository.existsById(1)).thenReturn(true);


        mockMvc.perform(delete("/admin/auteurs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "userTest", roles = "ADMIN")
    void deleteAuteurByIdButAuteurNotExistTest() throws Exception {
        mockMvc.perform(delete("/admin/auteurs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "userTest", roles = "ADMIN")
    void updateGoodAuteurTest() throws Exception {
        when(auteurRepository.existsById(1)).thenReturn(true);
        when(auteurRepository.findById(1)).thenReturn(java.util.Optional.of(new Auteur(1,"Costard", "Yohann", "yoyo4")));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        AuteurDTO auteurDTO = new AuteurDTO("Oui","Non", "Test");

        mockMvc.perform(put("/admin/auteurs/1").content(ow.writeValueAsString(auteurDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"nom\":\"Oui\",\"prenom\":\"Non\",\"pseudo\":\"Test\"}")));
    }

    @Test
    @WithMockUser(username = "userTest", roles = "ADMIN")
    void updateAuteurButNotExistTest() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        AuteurDTO auteurDTO = new AuteurDTO("Oui","Non", "Test");

        mockMvc.perform(put("/admin/auteurs/1").content(ow.writeValueAsString(auteurDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}