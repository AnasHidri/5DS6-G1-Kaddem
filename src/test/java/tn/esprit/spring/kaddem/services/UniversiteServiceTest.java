package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class UniversiteServiceTest {
    @Mock
    UniversiteRepository universiteRepository;
    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Mock
    DepartementRepository departementRepository;


    @Test
    //  @Order(1)
    public void testRetrieveUniversiteTest() {

        Universite universite = new Universite(1, "Université1");
        universite.setIdUniv(1);
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        universiteService.retrieveUniversite(1);
        assertNotNull(universite);

        System.out.println(universite);
        System.out.println("University Retrieve processed succefully...!!");

    }

    // @Order(2)
    @Test
    public void testAddUniversite() {
        Universite universiteToAdd = new Universite("esprit1");
        when(universiteRepository.save(universiteToAdd)).thenReturn(universiteToAdd);

        Universite addedUniversite = universiteService.addUniversite(universiteToAdd);

        verify(universiteRepository).save(universiteToAdd);
        assertNotNull(addedUniversite);
        assertEquals(universiteToAdd, addedUniversite);

        System.out.println(universiteToAdd);
        System.out.println("University added succefully...!!");
    }


  /*  @Test
    @Order(1)
    public void testRetrieveUniversiteTest() {
        Universite universite = new Universite(1, "Université1");
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        Universite retrievedUniversite = universiteService.retrieveUniversite(1);

        assertNotNull(retrievedUniversite);
        assertEquals(universite, retrievedUniversite);
    }
*/

    //   @Order(3)
    @Test
    public void testRetrieveAllUniversiteTest()
    {

        Universite university1 = new Universite("University 1");
        Universite university2 = new Universite("University 2");
        Universite university3 = new Universite("University 3");

        when(universiteRepository.findAll()).thenReturn(Arrays.asList(university1, university2, university3));

        List<Universite> allUniversities = universiteService.retrieveAllUniversites();

        Assertions.assertFalse(allUniversities.isEmpty());

        Assertions.assertEquals(3, allUniversities.size());
        System.out.println("All Universities Retrieve processed succefully...!!");
    }

    @Test
    //@Order(4)
    public void testUpdateUniversite() {

        Universite universiteToUpdate = new Universite(1, "central");

        when(universiteRepository.save(universiteToUpdate)).thenReturn(universiteToUpdate);

        Universite updatedUniversite = universiteService.updateUniversite(universiteToUpdate);

        verify(universiteRepository, times(1)).save(universiteToUpdate);

        assertEquals(universiteToUpdate, updatedUniversite);

        System.out.println(universiteToUpdate);
        System.out.println("University updated succefully...!!");
    }




    @Test
    public void testAssignUniversiteToDepartementTest() {
        Universite universite = new Universite(1, "univdep");
        Departement departement = new Departement(2, "Informatique");

        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(2)).thenReturn(Optional.of(departement));

        universiteService.assignUniversiteToDepartement(1, 2);

        verify(universiteRepository).save(universite);
        assertEquals(1, universite.getDepartements().size());
    }

    @Test
    public void RetrieveDepartementsByUniversiteTest() {

        Universite universite = new Universite(1, "univdep");

        Departement departement1 = new Departement(2, "Info");
        Departement departement2 = new Departement(3, "Tech");

        universite.getDepartements().add(departement1);
        universite.getDepartements().add(departement2);

        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        when(departementRepository.findAll()).thenReturn(Set.of(departement1, departement2));

        Set<Departement> retrievedDepartements = universiteService.retrieveDepartementsByUniversite(1);

        assertEquals(2, retrievedDepartements.size());

    }


}