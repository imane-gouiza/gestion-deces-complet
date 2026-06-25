package ma.chu.gestiondeces.alerte;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alertes")
@CrossOrigin(origins = "http://localhost:4200")
public class AlerteController {

    private final AlerteService alerteService;
    public AlerteController(AlerteService alerteService) {
        this.alerteService = alerteService;
    }

    @GetMapping("/non-lues")
    public List<Alerte> getAlertesNonLues() {
        return alerteService.getAlertesNonLues();
    }


    @GetMapping
    public List<Alerte> getToutesLesAlertes() {
        return alerteService.getToutesLesAlertes();
    }


    @GetMapping("/compteur")
    public Map<String, Long> getCompteur() {
        return Map.of(
                "total", alerteService.compterAlertesNonLues(),
                "critiques", alerteService.compterAlertesCritiques()
        );
    }


    @PutMapping("/{id}/lue")
    public ResponseEntity<Alerte> marquerCommeLue(@PathVariable Long id) {
        return ResponseEntity.ok(alerteService.marquerCommeLue(id));
    }


    @PostMapping("/verifier")
    public ResponseEntity<String> verifierRetards() {
        alerteService.verifierRetards();
        return ResponseEntity.ok("Vérification des retards effectuée.");
    }
}