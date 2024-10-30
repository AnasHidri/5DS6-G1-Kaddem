package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EtudiantServiceImplTest {

    @Mock
    EtudiantRepository etudiantRepository;

    @InjectMocks
    EtudiantServiceImpl etudiantService;

    @Test
    @Order(1)
    public void testRetrieveEtudiant() {
        // Given
        Etudiant etudiant = new Etudiant("Dupont", "Jean");
        etudiant.setIdEtudiant(1);

        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));

        // When
        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(1);

        // Then
        assertNotNull(retrievedEtudiant);
        assertEquals(etudiant, retrievedEtudiant);
    }

    @Test
    @Order(2)
    public void testAddEtudiant() {
        // Given
        Etudiant etudiantToAdd = new Etudiant("Dupont", "Jean");

        when(etudiantRepository.save(etudiantToAdd)).thenReturn(etudiantToAdd);

        // When
        Etudiant addedEtudiant = etudiantService.addEtudiant(etudiantToAdd);

        // Then
        verify(etudiantRepository, times(1)).save(etudiantToAdd);
        assertNotNull(addedEtudiant);
        assertEquals(etudiantToAdd, addedEtudiant);
    }

    @Test
    @Order(3)
    public void testRetrieveAllEtudiants() {
        // Given
        List<Etudiant> etudiantList = new ArrayList<Etudiant>() {
            {
                add(new Etudiant("Dupont", "Jean"));
                add(new Etudiant("Martin", "Paul"));
                add(new Etudiant("Lefevre", "Marie"));
                add(new Etudiant("Moreau", "Pierre"));
            }
        };

        when(etudiantRepository.findAll()).thenReturn(etudiantList);

        // When
        List<Etudiant> retrievedEtudiants = etudiantService.retrieveAllEtudiants();

        // Then
        assertEquals(4, retrievedEtudiants.size());
        System.out.println("All Etudiants Retrieved successfully!");
    }
}