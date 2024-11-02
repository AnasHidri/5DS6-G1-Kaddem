package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UniversiteServiceTest {

    @Mock
    UniversiteRepository universiteRepository;

    @Mock
    DepartementRepository departementRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void testRetrieveUniversite() {
        Universite universite = new Universite(1, "Université1");
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        Universite retrievedUniversite = universiteService.retrieveUniversite(1);

        assertNotNull(retrievedUniversite);
        assertEquals(universite, retrievedUniversite);
        verify(universiteRepository).findById(1);

        System.out.println("University Retrieve processed successfully...!!");
    }

    @Test
    @Order(2)
    void testAddUniversite() {
        Universite universiteToAdd = new Universite("Esprit1");
        when(universiteRepository.save(universiteToAdd)).thenReturn(universiteToAdd);

        Universite addedUniversite = universiteService.addUniversite(universiteToAdd);

        verify(universiteRepository).save(universiteToAdd);
        assertNotNull(addedUniversite);
        assertEquals(universiteToAdd, addedUniversite);

        System.out.println("University added successfully...!!");
    }

    @Test
    @Order(3)
    void testRetrieveAllUniversite() {
        Universite university1 = new Universite("University 1");
        Universite university2 = new Universite("University 2");
        Universite university3 = new Universite("University 3");

        when(universiteRepository.findAll()).thenReturn(Arrays.asList(university1, university2, university3));

        List<Universite> allUniversities = universiteService.retrieveAllUniversites();

        assertFalse(allUniversities.isEmpty());
        assertEquals(3, allUniversities.size());
        verify(universiteRepository).findAll();

        System.out.println("All Universities Retrieve processed successfully...!!");
    }

    @Test
    @Order(4)
    void testUpdateUniversite() {
        Universite universiteToUpdate = new Universite(1, "Central");
        when(universiteRepository.save(universiteToUpdate)).thenReturn(universiteToUpdate);

        Universite updatedUniversite = universiteService.updateUniversite(universiteToUpdate);

        verify(universiteRepository).save(universiteToUpdate);
        assertEquals(universiteToUpdate, updatedUniversite);

        System.out.println("University updated successfully...!!");
    }

    @Test
    @Order(5)
    void testAssignUniversiteToDepartement() {
        Universite universite = new Universite(1, "UnivDep");
        Departement departement = new Departement(2, "Informatique");

        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(2)).thenReturn(Optional.of(departement));

        universiteService.assignUniversiteToDepartement(1, 2);

        verify(universiteRepository).save(universite);
        assertEquals(1, universite.getDepartements().size());
    }

    @Test
    @Order(6)
    void testRetrieveDepartementsByUniversite() {
        Universite universite = new Universite(1, "UnivDep");
        Departement departement1 = new Departement(2, "Info");
        Departement departement2 = new Departement(3, "Tech");

        universite.getDepartements().add(departement1);
        universite.getDepartements().add(departement2);

        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        Set<Departement> retrievedDepartements = universiteService.retrieveDepartementsByUniversite(1);

        assertEquals(2, retrievedDepartements.size());
        verify(universiteRepository).findById(1);
    }
}
