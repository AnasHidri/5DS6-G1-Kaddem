package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class UniversiteServiceTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Test
    @Order(1)
    void testRetrieveUniversite() {
        Universite universite = new Universite(1, "Université1");
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        Universite retrievedUniversite = universiteService.retrieveUniversite(1);

        assertNotNull(retrievedUniversite, "The retrieved Universite should not be null");
        assertEquals(universite, retrievedUniversite, "The retrieved Universite should match the expected Universite");
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    @Order(2)
    void testAddUniversite() {
        Universite universiteToAdd = new Universite("Esprit1");
        when(universiteRepository.save(universiteToAdd)).thenReturn(universiteToAdd);

        Universite addedUniversite = universiteService.addUniversite(universiteToAdd);

        verify(universiteRepository, times(1)).save(universiteToAdd);
        assertNotNull(addedUniversite, "The added Universite should not be null");
        assertEquals(universiteToAdd, addedUniversite, "The added Universite should match the input Universite");
    }

    @Test
    @Order(3)
    void testRetrieveAllUniversite() {
        Universite university1 = new Universite("University 1");
        Universite university2 = new Universite("University 2");
        Universite university3 = new Universite("University 3");

        when(universiteRepository.findAll()).thenReturn(Arrays.asList(university1, university2, university3));

        List<Universite> allUniversities = universiteService.retrieveAllUniversites();

        assertNotNull(allUniversities, "The list of universities should not be null");
        assertFalse(allUniversities.isEmpty(), "The list of universities should not be empty");
        assertEquals(3, allUniversities.size(), "The list should contain 3 universities");
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    @Order(4)
    void testUpdateUniversite() {
        Universite universiteToUpdate = new Universite(1, "Central");
        when(universiteRepository.save(universiteToUpdate)).thenReturn(universiteToUpdate);

        Universite updatedUniversite = universiteService.updateUniversite(universiteToUpdate);

        verify(universiteRepository, times(1)).save(universiteToUpdate);
        assertEquals(universiteToUpdate, updatedUniversite, "The updated Universite should match the input Universite");
    }

}
