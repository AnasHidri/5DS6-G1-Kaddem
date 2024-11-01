package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ContratServiceImpl implements IContratService {
    @Autowired
    private ContratRepository contratRepository;
    
    @Autowired
    private EtudiantRepository etudiantRepository;

    public List<Contrat> retrieveAllContrats() {
        return contratRepository.findAll();
    }

    public Contrat updateContrat(Contrat ce) {
        if (ce == null) throw new RuntimeException("Contract cannot be null");
        return contratRepository.save(ce);
    }

    public Contrat addContrat(Contrat ce) {
        if (ce == null) throw new RuntimeException("Contract cannot be null");
        return contratRepository.save(ce);
    }

    public Contrat retrieveContrat(Integer idContrat) {
        if (idContrat == null) throw new RuntimeException("Contract ID cannot be null");
        return contratRepository.findById(idContrat)
            .orElseThrow(() -> new RuntimeException("Contract not found with id: " + idContrat));
    }

    public void removeContrat(Integer idContrat) {
        if (idContrat == null) throw new RuntimeException("Contract ID cannot be null");
        Contrat c = retrieveContrat(idContrat);
        contratRepository.delete(c);
    }

    public Contrat affectContratToEtudiant(Integer idContrat, String nomE, String prenomE) {
        // Input validation
        if (idContrat == null || nomE == null || prenomE == null) {
            throw new RuntimeException("All parameters are required");
        }

        // Retrieve contract and student
        Contrat contrat = contratRepository.findByIdContrat(idContrat);
        if (contrat == null) {
            throw new RuntimeException("Contract not found with id: " + idContrat);
        }

        Etudiant etudiant = etudiantRepository.findByNomEAndPrenomE(nomE, prenomE);
        if (etudiant == null) {
            throw new RuntimeException("Student not found with name: " + nomE + " " + prenomE);
        }

        // Initialize contracts set if null
        if (etudiant.getContrats() == null) {
            etudiant.setContrats(new HashSet<>());
        }

        // Count active contracts
        long activeContracts = etudiant.getContrats().stream()
            .filter(c -> c.getArchive() != null && !c.getArchive())
            .count();

        if (activeContracts >= 5) {
            throw new RuntimeException("Student already has maximum number of active contracts");
        }

        // Affect contract to student
        contrat.setEtudiant(etudiant);
        etudiant.getContrats().add(contrat);
        
        return contratRepository.save(contrat);
    }

    public Integer nbContratsValides(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start and end dates are required");
        }
        return contratRepository.getnbContratsValides(startDate, endDate);
    }

    public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start and end dates are required");
        }

        float differenceInDays = (endDate.getTime() - startDate.getTime()) / (1000.0f * 60 * 60 * 24);
        float differenceInMonths = differenceInDays / 30.0f;

        List<Contrat> contrats = contratRepository.findAll();
        float chiffreAffaire = 0.0f;

        for (Contrat contrat : contrats) {
            float monthlyRate = switch (contrat.getSpecialite()) {
                case IA -> 300;
                case CLOUD -> 400;
                case RESEAUX -> 350;
                case SECURITE -> 450;
            };
            chiffreAffaire += (differenceInMonths * monthlyRate);
        }

        return chiffreAffaire;
    }

    public void retrieveAndUpdateStatusContrat() {
        List<Contrat> contrats = contratRepository.findAll();
        Date dateSysteme = new Date();

        for (Contrat contrat : contrats) {
            if (Boolean.FALSE.equals(contrat.getArchive())) {
                long differenceInDays = (dateSysteme.getTime() - contrat.getDateFinContrat().getTime()) 
                    / (1000 * 60 * 60 * 24);

                if (differenceInDays == 15) {
                    log.info("Contract approaching end date: {}", contrat);
                } else if (differenceInDays >= 0) {
                    contrat.setArchive(true);
                    contratRepository.save(contrat);
                }
            }
        }
    }
}