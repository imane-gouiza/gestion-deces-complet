package spring_boot.deces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot.deces.dto.DecesRequest;
import spring_boot.deces.entity.DossierDeces;
import spring_boot.deces.entity.User;
import spring_boot.deces.repository.HistoriqueRepository;
import spring_boot.deces.repository.UserRepository;
import spring_boot.deces.service.DossierDecesService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/deces")
@CrossOrigin(origins = "*")
public class DossierDecesController {

    @Autowired
    private DossierDecesService dossierService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoriqueRepository historiqueRepository;

    @PostMapping("/ajouter")
    public ResponseEntity<?> ajouterDeces(@RequestBody DecesRequest request) {
        try {
            User baf = userRepository.findById(1L).orElse(null);

            if (baf == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "BAF non trouvé id=1 - vérifie ta table users");
                return ResponseEntity.badRequest().body(error);
            }

            DossierDeces dossier = dossierService.ajouterDeces(
                    request.getIpp(),
                    request.getCodeCadavre(),
                    baf.getId()
            );

            return ResponseEntity.ok(dossier);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API khdama mzyan !");
    }

    @GetMapping("/en-attente")
    public ResponseEntity<?> getEnAttente() {
        return ResponseEntity.ok(dossierService.getDossiersEnAttente());
    }

    @GetMapping("/prets-transfert")
    public ResponseEntity<?> getPretsTransfert() {
        return ResponseEntity.ok(dossierService.getDossiersPretsTransfert());
    }

    @PutMapping("/{id}/valider-certificat")
    public ResponseEntity<?> validerCertificat(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload,
            @RequestParam Long surveillantId) {
        try {
            DossierDeces dossier = dossierService.validerCertificat(
                    id,
                    payload.get("scannedCode"),
                    surveillantId
            );
            return ResponseEntity.ok(dossier);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/prise-en-charge")
    public ResponseEntity<?> priseEnCharge(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload,
            @RequestParam Long ambulancierId) {
        try {
            DossierDeces dossier = dossierService.confirmerPriseEnCharge(
                    id,
                    payload.get("scannedCode"),
                    ambulancierId
            );
            return ResponseEntity.ok(dossier);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/arrivee-morgue")
    public ResponseEntity<?> arriveeMorgue(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(dossierService.confirmerArriveeMorgue(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/anomalie")
    public ResponseEntity<?> signalerAnomalie(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        try {
            return ResponseEntity.ok(
                    dossierService.signalerAnomalie(id, payload.get("message"))
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/historique")
    public ResponseEntity<?> getHistorique(@PathVariable Long id) {
        return ResponseEntity.ok(
                historiqueRepository.findByDossierDecesId(id)
        );
    }
}