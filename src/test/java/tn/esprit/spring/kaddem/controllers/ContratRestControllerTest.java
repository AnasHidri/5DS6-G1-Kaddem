package tn.esprit.spring.kaddem.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.services.IContratService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(ContratRestController.class)
class ContratRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IContratService contratService;

    @InjectMocks
    private ContratRestController contratRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getContrats() throws Exception {
        List<Contrat> contrats = Arrays.asList(new Contrat(), new Contrat());
        when(contratService.retrieveAllContrats()).thenReturn(contrats);

        mockMvc.perform(get("/contrat/retrieve-all-contrats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveContrat() throws Exception {
        Integer contratId = 1;
        Contrat contrat = new Contrat();
        contrat.setIdContrat(contratId);
        when(contratService.retrieveContrat(contratId)).thenReturn(contrat);

        mockMvc.perform(get("/contrat/retrieve-contrat/{contrat-id}", contratId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idContrat").value(contratId));
    }

    @Test
    void addContrat() throws Exception {
        Contrat contrat = new Contrat();
        contrat.setIdContrat(1);
        when(contratService.addContrat(any(Contrat.class))).thenReturn(contrat);

        mockMvc.perform(post("/contrat/add-contrat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dateDebutContrat\":\"2024-01-01\",\"dateFinContrat\":\"2024-12-31\",\"montantContrat\":1000,\"archive\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idContrat").value(1));
    }

    @Test
    void removeContrat() throws Exception {
        Integer contratId = 1;
        doNothing().when(contratService).removeContrat(contratId);

        mockMvc.perform(delete("/contrat/remove-contrat/{contrat-id}", contratId))
                .andExpect(status().isOk());

        verify(contratService, times(1)).removeContrat(contratId);
    }

    @Test
    void updateContrat() throws Exception {
        Contrat contrat = new Contrat();
        contrat.setIdContrat(1);
        when(contratService.updateContrat(any(Contrat.class))).thenReturn(contrat);

        mockMvc.perform(put("/contrat/update-contrat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idContrat\":1,\"dateDebutContrat\":\"2024-01-01\",\"dateFinContrat\":\"2024-12-31\",\"montantContrat\":1000,\"archive\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idContrat").value(1));
    }

    // Add more tests for other endpoints as needed
}
