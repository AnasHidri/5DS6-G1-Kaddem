package tn.esprit.spring.kaddem.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;


import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest // Use this if you're testing JPA repositories
class ContratRepositoryTest {

    @Autowired
    private ContratRepository contratRepository;

    private Contrat contrat;

    @BeforeEach
    void setUp() {
        contrat = new Contrat();
        contrat.setDateDebutContrat(new Date());
        contrat.setDateFinContrat(new Date());
        contrat.setSpecialite(Specialite.IA); 
        contrat.setMontantContrat(1000);
        contratRepository.save(contrat);
    }

    @Test
    void testGetNbContratsValides() {
        Integer count = contratRepository.getnbContratsValides(new Date(System.currentTimeMillis() - 100000), new Date(System.currentTimeMillis() + 100000));
        assertEquals(1, count);
    }

    @Test
    void testFindByIdContrat() {
        Contrat foundContrat = contratRepository.findByIdContrat(contrat.getIdContrat());
        assertEquals(contrat.getIdContrat(), foundContrat.getIdContrat());
    }

    @Test
    void testFindAll() {
        List<Contrat> contrats = contratRepository.findAll();
        assertEquals(1, contrats.size());
    }
}
