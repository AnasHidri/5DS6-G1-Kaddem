package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

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
        List<Etudiant> etudiantList = new ArrayList<>() {
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
