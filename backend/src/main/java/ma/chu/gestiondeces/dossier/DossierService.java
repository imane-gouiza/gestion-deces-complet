package ma.chu.gestiondeces.dossier;

import ma.chu.gestiondeces.patient.Patient;
import ma.chu.gestiondeces.patient.PatientRepository;
import ma.chu.gestiondeces.user.Role;
import ma.chu.gestiondeces.user.User;
import ma.chu.gestiondeces.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DossierService {

    private final DossierRepository dossierRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public DossierService(DossierRepository dossierRepository, PatientRepository patientRepository, UserRepository userRepository) {
        this.dossierRepository = dossierRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    public DossierDeces createDossier(CreateDossierRequest request) {
        Patient patient = patientRepository.findByIpp(request.getIpp())
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        User agentBaf = userRepository.findByEmail(request.getEmailAgentBaf())
                .orElseThrow(() -> new RuntimeException("Agent BAF introuvable"));

        if (agentBaf.getRole() != Role.BAF) {
            throw new RuntimeException("Seul le BAF peut créer un dossier décès");
        }

        if (dossierRepository.existsByCodeCadavre(request.getCodeCadavre())) {
            throw new RuntimeException("Ce code cadavre est déjà utilisé");
        }

        String reference = "DOS-" + System.currentTimeMillis();

        DossierDeces dossier = new DossierDeces(
                reference,
                patient,
                request.getCodeCadavre(),
                StatutDossier.EN_ATTENTE_VERIFICATION,
                LocalDateTime.now(),
                agentBaf
        );

        return dossierRepository.save(dossier);
    }

    public DossierDeces validerCertificatParCode(String codeCadavre) {
        DossierDeces dossier = dossierRepository.findByCodeCadavre(codeCadavre)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable pour ce code cadavre"));

        if (dossier.getStatut() != StatutDossier.EN_ATTENTE_VERIFICATION) {
            throw new RuntimeException("Ce dossier n'est pas en attente de vérification");
        }

        dossier.setStatut(StatutDossier.PRET_POUR_TRANSFERT);

        return dossierRepository.save(dossier);
    }
}