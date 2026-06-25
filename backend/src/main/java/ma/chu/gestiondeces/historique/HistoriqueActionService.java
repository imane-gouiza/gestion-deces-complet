package ma.chu.gestiondeces.historique;

import ma.chu.gestiondeces.dossier.DossierDeces;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoriqueActionService {

    private final HistoriqueActionRepository repository;

    public HistoriqueActionService(HistoriqueActionRepository repository) {
        this.repository = repository;
    }

    public HistoriqueAction tracer(
            String utilisateur,
            String role,
            String action,
            String description,
            DossierDeces dossier
    ) {

        HistoriqueAction historique = new HistoriqueAction(
                utilisateur,
                role,
                action,
                description,
                LocalDateTime.now(),
                dossier
        );

        return repository.save(historique);
    }

    public void tracerCreation(DossierDeces dossier) {

        String utilisateur = "BAF";

        if (dossier.getCreePar() != null
                && dossier.getCreePar().getNom() != null) {

            utilisateur = dossier.getCreePar().getNom();
        }

        tracer(
                utilisateur,
                "BAF",
                "CREATION",
                "Dossier décès créé pour le patient "
                        + dossier.getPatient().getPrenom()
                        + " "
                        + dossier.getPatient().getNom(),
                dossier
        );
    }

    public void tracerValidation(DossierDeces dossier, String utilisateur) {

        tracer(
                utilisateur,
                "SURVEILLANT_GENERAL",
                "VALIDATION",
                "Validation du dossier décès",
                dossier
        );
    }

    public void tracerPriseEnCharge(DossierDeces dossier, String utilisateur) {

        tracer(
                utilisateur,
                "AMBULANCIER",
                "PRISE_EN_CHARGE",
                "Prise en charge du corps confirmée",
                dossier
        );
    }

    public void tracerArriveeMorgue(DossierDeces dossier, String utilisateur) {

        tracer(
                utilisateur,
                "AMBULANCIER",
                "ARRIVEE_MORGUE",
                "Arrivée à la morgue confirmée",
                dossier
        );
    }

    public void tracerAnomalie(
            DossierDeces dossier,
            String utilisateur,
            String description
    ) {

        tracer(
                utilisateur,
                "SYSTEM",
                "ANOMALIE",
                description,
                dossier
        );
    }

    public void tracerAlerte(
            DossierDeces dossier,
            String niveau,
            String description
    ) {

        tracer(
                "SYSTEM",
                "SYSTEM",
                "ALERTE_" + niveau,
                description,
                dossier
        );
    }

    public List<HistoriqueAction> getHistoriqueDossier(Long dossierId) {
        return repository.findByDossierIdOrderByDateActionDesc(dossierId);
    }

    public List<HistoriqueAction> getToutesLesActions() {
        return repository.findAllByOrderByDateActionDesc();
    }
}