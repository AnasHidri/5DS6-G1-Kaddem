package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tn.esprit.spring.kaddem.entities.Equipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class EquipeServiceImplTest {
    @Mock
    tn.esprit.spring.kaddem.repositories.EquipeRepository EquipeRepository;


    @InjectMocks
    EquipeServiceImpl equipeService;

    @Test
    @Order(1)
    public void testRetrieveEquipe() {
        // Given
        Equipe equipe = new Equipe("Ca");
        equipe.setIdEquipe(1);

        when(EquipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        // When
        Equipe retrievedEquipe = equipeService.retrieveEquipe(1);

        // Then
        assertNotNull(retrievedEquipe);
        assertEquals(equipe, retrievedEquipe);
    }

    @Test
    @Order(2)
    public void testAddEquipe() {
        // Given
        Equipe EquipeToAdd = new Equipe("Ca");

        when(EquipeRepository.save(EquipeToAdd)).thenReturn(EquipeToAdd);

        // When
        Equipe addedEquipe = equipeService.addEquipe(EquipeToAdd);

        // Then
        verify(EquipeRepository, times(1)).save(EquipeToAdd);
        assertNotNull(addedEquipe);
        assertEquals(EquipeToAdd, addedEquipe);
    }


    @Test
    @Order(3)
    public void testRetrieveAllTest() {
        List<Equipe> Equipelist = new ArrayList<Equipe>() {

            {
                add(new Equipe("Ca"));
                add(new Equipe("EST"));
                add(new Equipe("ESS"));
                add(new Equipe("CAB"));

            }
        };
        when(equipeService.retrieveAllEquipes()).thenReturn(Equipelist);
        List<Equipe> equipeList = equipeService.retrieveAllEquipes();
        assertEquals(4, equipeList.size());
        System.out.println("All Equipe Retrieved succefully...!!");
    }

}



