package ma.chu.gestiondeces.anomalie;

import ma.chu.gestiondeces.dossier.DossierDeces;
import ma.chu.gestiondeces.dossier.DossierRepository;
import ma.chu.gestiondeces.dossier.StatutDossier;
import ma.chu.gestiondeces.notification.NotificationService;
import ma.chu.gestiondeces.user.Role;
import ma.chu.gestiondeces.user.User;
import ma.chu.gestiondeces.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnomalieService {

    private final AnomalieRepository anomalieRepository;
    private final DossierRepository dossierRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public AnomalieService(AnomalieRepository anomalieRepository,
                           DossierRepository dossierRepository,
                           UserRepository userRepository,
                           NotificationService notificationService) {
        this.anomalieRepository = anomalieRepository;
        this.dossierRepository = dossierRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public Anomalie signalerAnomalie(CreateAnomalieRequest request) {
        if (request.getSignaleeParRole() != Role.SURVEILLANT_GENERAL
                && request.getSignaleeParRole() != Role.AMBULANCIER) {
            throw new RuntimeException("Seul le surveillant général ou l'ambulancier peut signaler une anomalie");
        }

        DossierDeces dossier = dossierRepository.findByCodeCadavre(request.getCodeCadavre())
                .orElseThrow(() -> new RuntimeException("Dossier introuvable pour ce code cadavre"));

        dossier.setStatut(StatutDossier.ANOMALIE_DETECTEE);
        DossierDeces dossierSaved = dossierRepository.save(dossier);

        Anomalie anomalie = new Anomalie(
                request.getTypeAnomalie(),
                request.getDescription(),
                request.getSignaleeParRole(),
                dossierSaved
        );

        Anomalie anomalieSaved = anomalieRepository.save(anomalie);


        String roleSignalant = request.getSignaleeParRole() == Role.SURVEILLANT_GENERAL
                ? "le Surveillant Général"
                : "l'Ambulancier";

        String messageEmail =
                "Bonjour Agent BAF,\n\n" +
                        "Une anomalie a été signalée par " + roleSignalant + ".\n\n" +
                        "Référence dossier : " + dossierSaved.getReferenceDossier() + "\n" +
                        "Code cadavre     : " + dossierSaved.getCodeCadavre() + "\n" +
                        "Type d'anomalie  : " + request.getTypeAnomalie() + "\n" +
                        "Description      : " + request.getDescription() + "\n\n" +
                        "Veuillez corriger cette anomalie dans l'application.\n\n" +
                        "-- Système de Gestion des Décès CHU --";

        notificationService.creerNotification(
                "🚨 Anomalie détectée — " + dossierSaved.getReferenceDossier(),
                messageEmail,
                Role.BAF,
                dossierSaved
        );

        return anomalieSaved;
    }

    public List<Anomalie> getAllAnomalies() {
        return anomalieRepository.findAllByOrderByDateSignalementDesc();
    }

    public List<Anomalie> getAnomaliesOuvertes() {
        return anomalieRepository.findByStatutOrderByDateSignalementDesc(StatutAnomalie.OUVERTE);
    }

    public Anomalie corrigerAnomalie(Long id, CorrigerAnomalieRequest request) {
        Anomalie anomalie = anomalieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anomalie introuvable"));

        if (anomalie.getStatut() != StatutAnomalie.OUVERTE) {
            throw new RuntimeException("Cette anomalie est déjà corrigée");
        }

        User baf = userRepository.findByEmail(request.getEmailBaf())
                .orElseThrow(() -> new RuntimeException("Agent BAF introuvable"));

        if (baf.getRole() != Role.BAF) {
            throw new RuntimeException("Seul le BAF peut corriger une anomalie");
        }

        if (request.getActionCorrective() == null || request.getActionCorrective().trim().isEmpty()) {
            throw new RuntimeException("L'action corrective est obligatoire");
        }

        DossierDeces dossier = anomalie.getDossier();

        String nouveauCode = request.getNouveauCodeCadavre();

        if (nouveauCode != null && !nouveauCode.trim().isEmpty()
                && !nouveauCode.equals(dossier.getCodeCadavre())) {

            if (dossierRepository.existsByCodeCadavre(nouveauCode)) {
                throw new RuntimeException("Ce nouveau code cadavre est déjà utilisé");
            }

            anomalie.setAncienCodeCadavre(dossier.getCodeCadavre());
            anomalie.setNouveauCodeCadavre(nouveauCode);
            dossier.setCodeCadavre(nouveauCode);
        }

        anomalie.setActionCorrective(request.getActionCorrective());
        anomalie.setCorrigeePar(baf);
        anomalie.setDateCorrection(LocalDateTime.now());
        anomalie.setStatut(StatutAnomalie.CORRIGEE);

        dossier.setStatut(StatutDossier.EN_ATTENTE_VERIFICATION);
        DossierDeces dossierSaved = dossierRepository.save(dossier);

        String messageCorrection =
                "Bonjour Surveillant Général,\n\n" +
                        "L'anomalie du dossier " + dossierSaved.getReferenceDossier() + " a été corrigée par le BAF.\n\n" +
                        "Code cadavre     : " + dossierSaved.getCodeCadavre() + "\n" +
                        "Action corrective: " + request.getActionCorrective() + "\n\n" +
                        "Le dossier est maintenant en attente de re-vérification.\n\n" +
                        "-- Système de Gestion des Décès CHU --";

        notificationService.creerNotification(
                "✅ Anomalie corrigée — " + dossierSaved.getReferenceDossier(),
                messageCorrection,
                Role.SURVEILLANT_GENERAL,
                dossierSaved
        );

        return anomalieRepository.save(anomalie);
    }
}