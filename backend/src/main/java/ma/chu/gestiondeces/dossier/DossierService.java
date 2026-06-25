package ma.chu.gestiondeces.dossier;

import ma.chu.gestiondeces.historique.HistoriqueActionService;
import ma.chu.gestiondeces.notification.NotificationService;
import ma.chu.gestiondeces.patient.Patient;
import ma.chu.gestiondeces.patient.PatientRepository;
import ma.chu.gestiondeces.user.Role;
import ma.chu.gestiondeces.user.User;
import ma.chu.gestiondeces.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DossierService {

    private final DossierRepository dossierRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final HistoriqueActionService historiqueService;

    public DossierService(
            DossierRepository dossierRepository,
            PatientRepository patientRepository,
            UserRepository userRepository,
            NotificationService notificationService,
            HistoriqueActionService historiqueService
    ) {
        this.dossierRepository = dossierRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.historiqueService = historiqueService;
    }


    private String genererNumeroOrdre() {
        int annee = LocalDateTime.now().getYear();
        long nombreDossiersAnnee = dossierRepository.countByAnnee(annee) + 1;
        return String.format("DEC-%d-%04d", annee, nombreDossiersAnnee);
    }

    public DossierDeces createDossier(CreateDossierRequest request) {

        Patient patient = patientRepository.findByIpp(request.getIpp())
                .orElseThrow(() -> new RuntimeException("Patient introuvable pour l'IPP : " + request.getIpp()));

        User agentBaf = userRepository.findByEmail(request.getEmailAgentBaf())
                .orElseThrow(() -> new RuntimeException("Agent BAF introuvable : " + request.getEmailAgentBaf()));

        if (agentBaf.getRole() != Role.BAF) {
            throw new RuntimeException("Seul le BAF peut créer un dossier décès");
        }

        // CORRECTION : utilise countDossiersByPatientIpp() > 0 à la place de
        // existsDossierByIpp() qui contenait SELECT COUNT(d) > 0 (JPQL invalide).
        if (dossierRepository.countDossiersByPatientIpp(request.getIpp()) > 0) {
            throw new RuntimeException("Ce patient possède déjà un dossier décès actif.");
        }

        if (dossierRepository.existsByCodeCadavre(request.getCodeCadavre())) {
            throw new RuntimeException("Ce code cadavre est déjà utilisé");
        }

        String numeroOrdre = genererNumeroOrdre();

        DossierDeces dossier = new DossierDeces(
                numeroOrdre,
                patient,
                request.getCodeCadavre(),
                StatutDossier.EN_ATTENTE_VERIFICATION,
                LocalDateTime.now(),
                agentBaf
        );
        dossier.setReferenceDossier("REF-" + System.currentTimeMillis());
        DossierDeces saved = dossierRepository.save(dossier);

        historiqueService.tracerCreation(saved);

        notificationService.creerNotification(
                "Nouveau dossier décès",
                "Un nouveau dossier décès est en attente de vérification : "
                        + saved.getNumeroOrdre(),
                Role.SURVEILLANT_GENERAL,
                saved
        );

        return saved;
    }

    public DossierDeces validerCertificatParCode(String codeCadavre) {

        DossierDeces dossier = dossierRepository.findByCodeCadavre(codeCadavre)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable pour le code : " + codeCadavre));

        if (dossier.getStatut() != StatutDossier.EN_ATTENTE_VERIFICATION) {
            throw new RuntimeException("Ce dossier n'est pas en attente de vérification (statut actuel : "
                    + dossier.getStatut() + ")");
        }

        dossier.setStatut(StatutDossier.PRET_POUR_TRANSFERT);
        DossierDeces saved = dossierRepository.save(dossier);

        historiqueService.tracerValidation(saved, "Surveillant Général");

        notificationService.creerNotification(
                "Dossier prêt pour transfert",
                "Le dossier " + saved.getNumeroOrdre() + " est prêt pour transfert.",
                Role.AMBULANCIER,
                saved
        );

        return saved;
    }

    /**
     * ÉTAPE 3
     * Confirmation prise en charge par Ambulancier.
     */
    public DossierDeces confirmerPriseEnCharge(String codeCadavre) {

        DossierDeces dossier = dossierRepository.findByCodeCadavre(codeCadavre)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable pour le code : " + codeCadavre));

        if (dossier.getStatut() != StatutDossier.PRET_POUR_TRANSFERT) {
            throw new RuntimeException("Ce dossier n'est pas prêt pour le transfert (statut actuel : "
                    + dossier.getStatut() + ")");
        }

        dossier.setStatut(StatutDossier.EN_COURS_DE_TRANSFERT);
        DossierDeces saved = dossierRepository.save(dossier);

        historiqueService.tracerPriseEnCharge(saved, "Ambulancier");

        notificationService.creerNotification(
                "Prise en charge confirmée",
                "L'ambulancier a confirmé la prise en charge du dossier "
                        + saved.getNumeroOrdre() + ".",
                Role.BAF,
                saved
        );

        return saved;
    }

    /**
     * ÉTAPE 4
     * Confirmation arrivée morgue.
     */
    public DossierDeces confirmerArriveeMorgue(String codeCadavre) {

        DossierDeces dossier = dossierRepository.findByCodeCadavre(codeCadavre)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable pour le code : " + codeCadavre));

        if (dossier.getStatut() != StatutDossier.EN_COURS_DE_TRANSFERT) {
            throw new RuntimeException("Ce dossier n'est pas en cours de transfert (statut actuel : "
                    + dossier.getStatut() + ")");
        }

        dossier.setStatut(StatutDossier.CLOTURE);
        DossierDeces saved = dossierRepository.save(dossier);

        historiqueService.tracerArriveeMorgue(saved, "Ambulancier");

        notificationService.creerNotification(
                "Dossier clôturé",
                "Le dossier " + saved.getNumeroOrdre()
                        + " est clôturé après confirmation d'arrivée à la morgue.",
                Role.BAF,
                saved
        );

        return saved;
    }

    /**
     * Recherche avancée multi-critères sur les dossiers décès.
     */
    public List<DossierDeces> searchDossiers(
            String q,
            String numeroOrdre,
            String ipp,
            String nom,
            String codeCadavre,
            String service,
            String statut
    ) {
        List<DossierDeces> all = dossierRepository.findAll();

        return all.stream()
                .filter(d -> matchesDossier(d, q, numeroOrdre, ipp, nom, codeCadavre, service, statut))
                .sorted(Comparator.comparing(
                        DossierDeces::getDateCreation,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .collect(Collectors.toList());
    }

    private boolean matchesDossier(
            DossierDeces d,
            String q,
            String numeroOrdre,
            String ipp,
            String nom,
            String codeCadavre,
            String service,
            String statut
    ) {
        Patient p = d.getPatient();

        if (hasValue(statut)) {
            try {
                if (d.getStatut() != StatutDossier.valueOf(statut)) {
                    return false;
                }
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        if (hasValue(q)) {
            String ql = q.toLowerCase();
            boolean globalMatch =
                    contains(d.getNumeroOrdre(), ql)  ||
                            contains(d.getCodeCadavre(), ql)  ||
                            (p != null && (
                                    contains(p.getIpp(),            ql) ||
                                            contains(p.getNom(),            ql) ||
                                            contains(p.getPrenom(),         ql) ||
                                            contains(p.getServiceOrigine(), ql)
                            ));
            if (!globalMatch) return false;
        }

        if (hasValue(numeroOrdre) && !contains(d.getNumeroOrdre(), numeroOrdre.toLowerCase())) return false;
        if (hasValue(codeCadavre) && !contains(d.getCodeCadavre(), codeCadavre.toLowerCase())) return false;

        if (p != null) {
            if (hasValue(ipp) && !contains(p.getIpp(), ipp.toLowerCase())) return false;
            if (hasValue(nom)) {
                String nomL = nom.toLowerCase();
                if (!contains(p.getNom(), nomL) && !contains(p.getPrenom(), nomL)) return false;
            }
            if (hasValue(service) && !contains(p.getServiceOrigine(), service.toLowerCase())) return false;
        }

        return true;
    }

    private boolean hasValue(String s) {
        return s != null && !s.isBlank();
    }

    private boolean contains(String field, String query) {
        return field != null && field.toLowerCase().contains(query);
    }
}