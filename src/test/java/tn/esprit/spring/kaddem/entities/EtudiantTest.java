package tn.esprit.spring.kaddem.entities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
public class EtudiantTest {
    @Test
    public void testEtudiantConstructor() {
        // Given
        String nom = "Gharbi";
        String prenom = "Skander";
        Option option = Option.SE;

        // When
        Etudiant etudiant = new Etudiant(nom, prenom, option);

        // Then
        assertEquals("Gharbi", etudiant.getNomE());
        assertEquals("Skander", etudiant.getPrenomE());
        assertEquals(Option.SE, etudiant.getOp());
    }


}
