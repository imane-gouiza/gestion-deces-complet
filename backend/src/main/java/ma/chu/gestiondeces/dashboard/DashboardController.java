package ma.chu.gestiondeces.dashboard;

import ma.chu.gestiondeces.dossier.DossierRepository;
import ma.chu.gestiondeces.dossier.StatutDossier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    private final DossierRepository dossierRepository;

    public DashboardController(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    @GetMapping("/stats")
    public DashboardStats getStats() {
        return new DashboardStats(
                dossierRepository.count(),
                dossierRepository.countByStatut(StatutDossier.EN_ATTENTE_VERIFICATION),
                dossierRepository.countByStatut(StatutDossier.PRET_POUR_TRANSFERT),
                dossierRepository.countByStatut(StatutDossier.EN_COURS_DE_TRANSFERT),
                dossierRepository.countByStatut(StatutDossier.CLOTURE)
        );
    }
}