package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;
    
    @Mock
    private EtudiantRepository etudiantRepository;
    
    @InjectMocks
    private ContratServiceImpl contratService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void testRetrieveAllContrats() {
        // Arrange
        List<Contrat> contrats = new ArrayList<>();
        contrats.add(new Contrat(new Date(), new Date(), Specialite.IA, false, 1));
        contrats.add(new Contrat(new Date(), new Date(), Specialite.CLOUD, false, 2));
        
        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        List<Contrat> result = contratService.retrieveAllContrats();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    void testAddContrat() {
        // Arrange
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        Contrat result = contratService.addContrat(contrat);

        // Assert
        assertNotNull(result);
        verify(contratRepository, times(1)).save(any(Contrat.class));
    }

    @Test
    @Order(3)
    void testRetrieveContrat() {
        // Arrange
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1);
        contrat.setIdContrat(1);
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        // Act
        Contrat result = contratService.retrieveContrat(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdContrat());
        verify(contratRepository, times(1)).findById(1);
    }

    @Test
    @Order(4)
    void testNbContratsValides() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date();
        when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(5);

        // Act
        Integer result = contratService.nbContratsValides(startDate, endDate);

        // Assert
        assertEquals(5, result);
        verify(contratRepository, times(1)).getnbContratsValides(startDate, endDate);
    }

    @Test
    @Order(5)
    void testGetChiffreAffaireEntreDeuxDates() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date();
        List<Contrat> contrats = List.of(
            new Contrat(startDate, endDate, Specialite.IA, false, 1),
            new Contrat(startDate, endDate, Specialite.CLOUD, false, 2)
        );
        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        // Assert
        assertTrue(result >= 0);
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    @Order(6)
    void testAffectContratToEtudiant() {
        // Arrange
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1);
        contrat.setIdContrat(1);
        
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("TestNom");
        etudiant.setPrenomE("TestPrenom");
        
        when(contratRepository.findByIdContrat(1)).thenReturn(contrat);
        when(etudiantRepository.findByNomEAndPrenomE("TestNom", "TestPrenom")).thenReturn(etudiant);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        Contrat result = contratService.affectContratToEtudiant(1, "TestNom", "TestPrenom");

        // Assert
        assertNotNull(result);
        verify(contratRepository, times(1)).findByIdContrat(1);
        verify(etudiantRepository, times(1)).findByNomEAndPrenomE("TestNom", "TestPrenom");
        verify(contratRepository, times(1)).save(any(Contrat.class));
    }

    // Error cases
    @Test
    @Order(7)
    void testRetrieveContratNotFound() {
        // Arrange
        when(contratRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            contratService.retrieveContrat(99);
        });
    }
}