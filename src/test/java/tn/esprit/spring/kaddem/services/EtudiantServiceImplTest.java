package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;



import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EtudiantServiceImplTest {

    @Mock
    EtudiantRepository etudiantRepository;

    @Mock
    ContratRepository contratRepository;

    @Mock
    EquipeRepository equipeRepository;

    @Mock
    DepartementRepository departementRepository;

    @InjectMocks
    EtudiantServiceImpl etudiantService;

    @Test
    @Order(1)
    void testRetrieveAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant());
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();
        verify(etudiantRepository, times(1)).findAll();
        assertEquals(1, result.size());
    }

    @Test
    @Order(2)
    void testAddEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.addEtudiant(etudiant);
        verify(etudiantRepository, times(1)).save(etudiant);
        assertNotNull(result);
    }

    @Test
    @Order(3)
    void testUpdateEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.updateEtudiant(etudiant);
        verify(etudiantRepository, times(1)).save(etudiant);
        assertNotNull(result);
    }

    @Test
    @Order(4)
    void testRetrieveEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.retrieveEtudiant(1);
        verify(etudiantRepository, times(1)).findById(1);
        assertNotNull(result);
    }

    @Test
    @Order(5)
    void testRemoveEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));

        etudiantService.removeEtudiant(1);
        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    
}
