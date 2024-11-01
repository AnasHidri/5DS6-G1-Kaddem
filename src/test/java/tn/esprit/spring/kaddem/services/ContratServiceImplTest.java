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

import java.util.*;
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
        verify(contratRepository).findAll();
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
        verify(contratRepository).save(any(Contrat.class));
    }

    @Test
    @Order(3)
    void testUpdateContrat() {
        // Arrange
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        Contrat result = contratService.updateContrat(contrat);

        // Assert
        assertNotNull(result);
        verify(contratRepository).save(any(Contrat.class));
    }

    @Test
    @Order(4)
    void testRetrieveContrat() {
        // Arrange
        Integer idContrat = 1;
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1);
        when(contratRepository.findById(idContrat)).thenReturn(Optional.of(contrat));

        // Act
        Contrat result = contratService.retrieveContrat(idContrat);

        // Assert
        assertNotNull(result);
        verify(contratRepository).findById(idContrat);
    }

    @Test
    @Order(5)
    void testRemoveContrat() {
        // Arrange
        Integer idContrat = 1;
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1);
        when(contratRepository.findById(idContrat)).thenReturn(Optional.of(contrat));

        // Act
        contratService.removeContrat(idContrat);

        // Assert
        verify(contratRepository).delete(any(Contrat.class));
    }

    @Test
    @Order(6)
    void testAffectContratToEtudiant() {
        // Arrange
        Integer idContrat = 1;
        String nomE = "testNom";
        String prenomE = "testPrenom";

        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1);
        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(new HashSet<>());
        
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);
        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        // Assert
        assertNotNull(result);
        verify(contratRepository).findByIdContrat(idContrat);
        verify(etudiantRepository).findByNomEAndPrenomE(nomE, prenomE);
        verify(contratRepository).save(any(Contrat.class));
    }

    @Test
    @Order(7)
    void testNbContratsValides() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date();
        when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(5);

        // Act
        Integer result = contratService.nbContratsValides(startDate, endDate);

        // Assert
        assertEquals(5, result);
        verify(contratRepository).getnbContratsValides(startDate, endDate);
    }

    @Test
    @Order(8)
    void testGetChiffreAffaireEntreDeuxDates() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 30L * 24 * 60 * 60 * 1000); // 30 days later
        List<Contrat> contrats = Arrays.asList(
            new Contrat(startDate, endDate, Specialite.IA, false, 1),
            new Contrat(startDate, endDate, Specialite.CLOUD, false, 2)
        );
        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        // Assert
        assertTrue(result >= 0);
        verify(contratRepository).findAll();
    }

    @Test
    @Order(9)
    void testRetrieveAndUpdateStatusContrat() {
        // Arrange
        List<Contrat> contrats = new ArrayList<>();
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1);
        contrats.add(contrat);
        when(contratRepository.findAll()).thenReturn(contrats);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        contratService.retrieveAndUpdateStatusContrat();

        // Assert
        verify(contratRepository).findAll();
    }
}