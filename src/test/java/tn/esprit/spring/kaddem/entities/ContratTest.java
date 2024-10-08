package tn.esprit.spring.kaddem.entities;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ContratTest {

    @Test
    void testCreateContrat() {
        Contrat contrat = new Contrat();
        contrat.setDateDebutContrat(new Date());
        contrat.setDateFinContrat(new Date());
        contrat.setMontantContrat(1000);
        contrat.setArchive(false);

        assertNotNull(contrat);
        assertEquals(1000, contrat.getMontantContrat());
        assertFalse(contrat.getArchive());
    }
}
