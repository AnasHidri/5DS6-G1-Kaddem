package tn.esprit.spring.kaddem.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;


public class ContratServiceImplTest {

    @InjectMocks
    private ContratServiceImpl contratService;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    private Contrat contrat;
    private Etudiant etudiant;

    @BeforeEach
    public void setUp() {
        
        MockitoAnnotations.openMocks(this);
        contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setSpecialite(Specialite.IA);
        contrat.setArchive(false);
        contrat.setDateFinContrat(new Date(System.currentTimeMillis() + 86400000)); // 1 day in future

        etudiant = new Etudiant();
        etudiant.setNomE("John");
        etudiant.setPrenomE("Doe");
        etudiant.setContrats(new ArrayList<>());
    }

    @Test
    public void testRetrieveAllContrats() {
        List<Contrat> contrats = new ArrayList<>();
        contrats.add(contrat);

  
        when(contratRepository.findAll()).thenReturn(contrats);

        List<Contrat> result = contratService.retrieveAllContrats();

        assertEquals(1, result.size());
        verify(contratRepository, times(1)).findAll();
    }
    @Test
    public void testAddContrat() {
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        Contrat result = contratService.addContrat(contrat);

        assert result != null;
        assert result.getIdContrat() == 1;
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    public void testRetrieveContrat() {
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        Contrat result = contratService.retrieveContrat(1);

        assert result != null;
        assert result.getIdContrat() == 1;
        verify(contratRepository, times(1)).findById(1);
    }

    @Test
    public void testRemoveContrat() {
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        contratService.removeContrat(1);

        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    public void testAffectContratToEtudiant() {
        when(contratRepository.findByIdContrat(1)).thenReturn(contrat);
        when(etudiantRepository.findByNomEAndPrenomE("John", "Doe")).thenReturn(etudiant);

        Contrat result = contratService.affectContratToEtudiant(1, "John", "Doe");

        assert result.getEtudiant() == etudiant;
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    public void testGetChiffreAffaireEntreDeuxDates() {
        Date startDate = new Date(System.currentTimeMillis() - 86400000); // 1 day in past
        Date endDate = new Date();
        when(contratRepository.findAll()).thenReturn(List.of(contrat));

        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        assert result > 0; // Assuming the calculation yields a positive number
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    public void testNbContratsValides() {
        Date startDate = new Date(System.currentTimeMillis() - 86400000); // 1 day in past
        Date endDate = new Date();

        when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(5);

        Integer result = contratService.nbContratsValides(startDate, endDate);

        assert result == 5;
        verify(contratRepository, times(1)).getnbContratsValides(startDate, endDate);
    }
}
