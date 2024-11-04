package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipeServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @InjectMocks
    private EquipeServiceImpl equipeService;

    @Test
    @Order(1)
    public void testRetrieveEquipe() {

        Equipe equipe = new Equipe("Ca");
        equipe.setIdEquipe(1);

        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));


        Equipe retrievedEquipe = equipeService.retrieveEquipe(1);


        assertNotNull(retrievedEquipe, "L'équipe récupérée ne doit pas être null");
        assertEquals(equipe, retrievedEquipe, "L'équipe récupérée doit correspondre à celle mockée");
    }

    @Test
    @Order(2)
    public void testAddEquipe() {
        // Given
        Equipe equipeToAdd = new Equipe("Ca");

        when(equipeRepository.save(equipeToAdd)).thenReturn(equipeToAdd);


        Equipe addedEquipe = equipeService.addEquipe(equipeToAdd);


        verify(equipeRepository, times(1)).save(equipeToAdd);
        assertNotNull(addedEquipe, "L'équipe ajoutée ne doit pas être null");
        assertEquals(equipeToAdd, addedEquipe, "L'équipe ajoutée doit correspondre à celle mockée");
    }

    @Test
    @Order(3)
    public void testRetrieveAllEquipes() {
        // Given
        List<Equipe> equipeList = new ArrayList<>();
        equipeList.add(new Equipe("Ca"));
        equipeList.add(new Equipe("EST"));
        equipeList.add(new Equipe("ESS"));
        equipeList.add(new Equipe("CAB"));

        when(equipeRepository.findAll()).thenReturn(equipeList);


        List<Equipe> result = equipeService.retrieveAllEquipes();


        assertNotNull(result, "La liste des équipes ne doit pas être null");
        assertEquals(4, result.size(), "La taille de la liste des équipes doit être de 4");
        System.out.println("All Equipes retrieved successfully!");
    }
}
