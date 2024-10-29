package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
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

    @Test
    @Order(6)
    void testAssignEtudiantToDepartement() {
        Etudiant etudiant = new Etudiant();
        Departement departement = new Departement();

        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        etudiantService.assignEtudiantToDepartement(1, 1);
        verify(etudiantRepository, times(1)).save(etudiant);
        assertEquals(departement, etudiant.getDepartement());
    }

    @Test
    @Order(7)
    void testAddAndAssignEtudiantToEquipeAndContract() {
        Etudiant etudiant = new Etudiant();
        Contrat contrat = new Contrat();
        Equipe equipe = new Equipe();

        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);
        
        assertEquals(etudiant, contrat.getEtudiant());
        assertTrue(equipe.getEtudiants().contains(etudiant));
        assertEquals(etudiant, result);
    }

    @Test
    @Order(8)
    void testGetEtudiantsByDepartement() {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant());
        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(1)).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.getEtudiantsByDepartement(1);
        verify(etudiantRepository, times(1)).findEtudiantsByDepartement_IdDepart(1);
        assertEquals(1, result.size());
    }
}
