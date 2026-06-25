package ma.chu.gestiondeces.alerte;

import ma.chu.gestiondeces.dossier.DossierDeces;
import ma.chu.gestiondeces.dossier.DossierRepository;
import ma.chu.gestiondeces.dossier.StatutDossier;
import ma.chu.gestiondeces.historique.HistoriqueActionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AlerteService {

    private final AlerteRepository alerteRepository;
    private final DossierRepository dossierRepository;
    private final HistoriqueActionService historiqueService;

    public AlerteService(AlerteRepository alerteRepository,
                         DossierRepository dossierRepository,
                         HistoriqueActionService historiqueService) {
        this.alerteRepository = alerteRepository;
        this.dossierRepository = dossierRepository;
        this.historiqueService = historiqueService;
    }

    @Scheduled(fixedDelay = 900000, initialDelay = 60000)
    public void verifierRetards() {
        LocalDateTime maintenant = LocalDateTime.now();

        List<StatutDossier> statutsActifs = List.of(
                StatutDossier.EN_ATTENTE_VERIFICATION,
                StatutDossier.PRET_POUR_TRANSFERT,
                StatutDossier.EN_COURS_DE_TRANSFERT
        );

        for (StatutDossier statut : statutsActifs) {
            List<DossierDeces> dossiers = dossierRepository.findByStatut(statut);

            for (DossierDeces dossier : dossiers) {
                if (dossier.getDateCreation() == null) continue;

                long heuresEcoulees  = ChronoUnit.HOURS.between(dossier.getDateCreation(), maintenant);
                long minutesEcoulees = ChronoUnit.MINUTES.between(dossier.getDateCreation(), maintenant);


                if (minutesEcoulees >= 120) {
                    if (alerteRepository.countAlertesActivesForDossier(dossier.getId(), NiveauAlerte.CRITICAL) == 0) {
                        String msg = buildMessageCritical(dossier, statut, heuresEcoulees);
                        creerAlerte(msg, NiveauAlerte.CRITICAL, dossier);
                        historiqueService.tracerAlerte(dossier, "CRITICAL", msg);
                    }
                } else if (minutesEcoulees >= 60) {
                    if (alerteRepository.countAlertesActivesForDossier(dossier.getId(), NiveauAlerte.WARNING) == 0) {
                        String msg = buildMessageWarning(dossier, statut, minutesEcoulees);
                        creerAlerte(msg, NiveauAlerte.WARNING, dossier);
                        historiqueService.tracerAlerte(dossier, "WARNING", msg);
                    }
                }
            }
        }
    }

    private String buildMessageWarning(DossierDeces dossier, StatutDossier statut, long minutes) {
        return String.format(
                "⚠ Dossier %s en attente depuis %d minutes — Statut : %s — Patient : %s %s",
                dossier.getNumeroOrdre(), minutes,
                formatStatut(statut),
                dossier.getPatient().getPrenom(), dossier.getPatient().getNom()
        );
    }

    private String buildMessageCritical(DossierDeces dossier, StatutDossier statut, long heures) {
        return String.format(
                "🔴 CRITIQUE : Dossier %s bloqué depuis %dh — Statut : %s — Patient : %s %s — Action immédiate requise !",
                dossier.getNumeroOrdre(), heures,
                formatStatut(statut),
                dossier.getPatient().getPrenom(), dossier.getPatient().getNom()
        );
    }

    private String formatStatut(StatutDossier statut) {
        return switch (statut) {
            case EN_ATTENTE_VERIFICATION -> "En attente de validation";
            case PRET_POUR_TRANSFERT     -> "Prêt pour transfert";
            case EN_COURS_DE_TRANSFERT   -> "Transfert en cours";
            default                      -> statut.name();
        };
    }

    public Alerte creerAlerte(String message, NiveauAlerte niveau, DossierDeces dossier) {
        Alerte alerte = new Alerte(message, niveau, LocalDateTime.now(), false, dossier);
        return alerteRepository.save(alerte);
    }

    public Alerte marquerCommeLue(Long alerteId) {
        Alerte alerte = alerteRepository.findById(alerteId)
                .orElseThrow(() -> new RuntimeException("Alerte introuvable"));
        alerte.setLu(true);
        return alerteRepository.save(alerte);
    }

    public List<Alerte> getAlertesNonLues() {
        return alerteRepository.findNonLues();
    }

    public List<Alerte> getToutesLesAlertes() {
        return alerteRepository.findAllOrderByDateCreationDesc();
    }

    public long compterAlertesNonLues() {
        return alerteRepository.countByLuFalse();
    }

    public long compterAlertesCritiques() {
        return alerteRepository.countByNiveauAndLuFalse(NiveauAlerte.CRITICAL);
    }
}