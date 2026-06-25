package spring_boot.deces.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_boot.deces.entity.*;
import spring_boot.deces.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DossierDecesService {

    @Autowired
    private DossierDecesRepository dossierRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoriqueRepository historiqueRepository;

    public DossierDeces ajouterDeces(String ipp, String codeCadavre, Long bafId) {

        Patient patient = patientRepository.findByIpp(ipp);
        if (patient == null) {
            throw new RuntimeException("Patient introuvable avec IPP : " + ipp);
        }

        if (dossierRepository.existsByCodeCadavre(codeCadavre)) {
            throw new RuntimeException("Ce code cadavre est déjà utilisé");
        }

        User baf = userRepository.findById(bafId)
                .orElseThrow(() -> new RuntimeException("BAF introuvable"));

        DossierDeces dossier = new DossierDeces();
        dossier.setIpp(patient.getIpp());
        dossier.setCodeCadavre(codeCadavre);
        dossier.setNomPatient(patient.getNom());
        dossier.setPrenomPatient(patient.getPrenom());
        dossier.setServiceOrigine(patient.getServiceOrigine());
        dossier.setStatut("EN_ATTENTE_VERIFICATION");
        dossier.setDateCreation(LocalDateTime.now());
        dossier.setBaf(baf);

        DossierDeces saved = dossierRepository.save(dossier);

        historiqueRepository.save(
                new Historique(saved, baf, "AJOUT_DECES", "Décès ajouté par le BAF")
        );

        return saved;
    }

    public List<DossierDeces> getDossiersEnAttente() {
        return dossierRepository.findByStatut("EN_ATTENTE_VERIFICATION");
    }

    public DossierDeces validerCertificat(Long id, String scannedCode, Long surveillantId) {

        DossierDeces dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        if (!dossier.getCodeCadavre().equals(scannedCode)) {
            dossier.setStatut("ANOMALIE_DETECTEE");
            dossier.setAnomalieMessage("Code scanné incorrect");
            dossierRepository.save(dossier);
            throw new RuntimeException("Code scanné incorrect");
        }

        User surveillant = userRepository.findById(surveillantId)
                .orElseThrow(() -> new RuntimeException("Surveillant introuvable"));

        dossier.setStatut("PRET_POUR_TRANSFERT");
        dossier.setSurveillant(surveillant);
        dossier.setDateValidationCertificat(LocalDateTime.now());

        DossierDeces saved = dossierRepository.save(dossier);

        historiqueRepository.save(
                new Historique(saved, surveillant, "VALIDATION_CERTIFICAT", "Certificat conforme validé")
        );

        return saved;
    }
    public List<DossierDeces> getDossiersPretsTransfert() {
        return dossierRepository.findByStatut("PRET_POUR_TRANSFERT");
    }

    public DossierDeces confirmerPriseEnCharge(Long id, String scannedCode, Long ambulancierId) {

        DossierDeces dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        if (!dossier.getCodeCadavre().equals(scannedCode)) {
            dossier.setStatut("ANOMALIE_DETECTEE");
            dossier.setAnomalieMessage("Code scanné incorrect");
            dossierRepository.save(dossier);
            throw new RuntimeException("Code scanné incorrect");
        }

        User ambulancier = userRepository.findById(ambulancierId)
                .orElseThrow(() -> new RuntimeException("Ambulancier introuvable"));

        dossier.setAmbulancier(ambulancier);
        dossier.setStatut("EN_COURS_TRANSFERT");
        dossier.setDatePriseEnCharge(LocalDateTime.now());

        DossierDeces saved = dossierRepository.save(dossier);

        historiqueRepository.save(
                new Historique(saved, ambulancier, "PRISE_EN_CHARGE", "Corps pris en charge par ambulancier")
        );

        return saved;
    }

    public DossierDeces confirmerArriveeMorgue(Long id) {

        DossierDeces dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        dossier.setStatut("CLOTURE");
        dossier.setDateArriveeMorgue(LocalDateTime.now());

        return dossierRepository.save(dossier);
    }
    public DossierDeces signalerAnomalie(Long id, String message) {

        DossierDeces dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        dossier.setStatut("ANOMALIE_DETECTEE");
        dossier.setAnomalieMessage(message);

        return dossierRepository.save(dossier);
    }
}