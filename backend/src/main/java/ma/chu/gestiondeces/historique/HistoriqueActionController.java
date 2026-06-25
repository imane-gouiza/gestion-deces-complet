package ma.chu.gestiondeces.historique;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/historique")
@CrossOrigin(origins = "http://localhost:4200")
public class HistoriqueActionController {

    private final HistoriqueActionService service;

    public HistoriqueActionController(HistoriqueActionService service) {
        this.service = service;
    }

    @GetMapping("/dossier/{dossierId}")
    public List<HistoriqueAction> getHistoriqueDossier(
            @PathVariable Long dossierId
    ) {
        return service.getHistoriqueDossier(dossierId);
    }


    @GetMapping
    public List<HistoriqueAction> getToutesLesActions() {
        return service.getToutesLesActions();
    }
}