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
        verify(contratRepository).findById(1);
    }

    @Test
    @Order(4)
    void testAffectContratToEtudiant() {
        // Arrange
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1);
        contrat.setIdContrat(1);
        
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("TestNom");
        etudiant.setPrenomE("TestPrenom");
        etudiant.setContrats(new HashSet<>());
        
        when(contratRepository.findByIdContrat(1)).thenReturn(contrat);
        when(etudiantRepository.findByNomEAndPrenomE("TestNom", "TestPrenom")).thenReturn(etudiant);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        Contrat result = contratService.affectContratToEtudiant(1, "TestNom", "TestPrenom");

        // Assert
        assertNotNull(result);
        assertNotNull(result.getEtudiant());
        assertEquals("TestNom", result.getEtudiant().getNomE());
        verify(contratRepository).findByIdContrat(1);
        verify(etudiantRepository).findByNomEAndPrenomE("TestNom", "TestPrenom");
        verify(contratRepository).save(any(Contrat.class));
    }

    @Test
    @Order(5)
    void testRetrieveContratNotFound() {
        // Arrange
        when(contratRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            contratService.retrieveContrat(99);
        });
    }

    @Test
    @Order(6)
    void testGetChiffreAffaireEntreDeuxDates() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 30L * 24 * 60 * 60 * 1000); // 30 days later
        List<Contrat> contrats = List.of(
            new Contrat(startDate, endDate, Specialite.IA, false, 1),
            new Contrat(startDate, endDate, Specialite.CLOUD, false, 2)
        );
        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        // Assert
        assertTrue(result > 0);
        assertEquals(700.0f, result, 0.1f); // 300 + 400 for one month
        verify(contratRepository).findAll();
    }
}