package ma.chu.gestiondeces.dossier;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dossiers")
@CrossOrigin(origins = "http://localhost:4200")
public class DossierController {

    private final DossierService dossierService;
    private final DossierRepository dossierRepository;

    public DossierController(DossierService dossierService, DossierRepository dossierRepository) {
        this.dossierService = dossierService;
        this.dossierRepository = dossierRepository;
    }

    @PostMapping
    public DossierDeces createDossier(@RequestBody CreateDossierRequest request) {
        return dossierService.createDossier(request);
    }

    @GetMapping
    public List<DossierDeces> getAllDossiers() {
        return dossierRepository.findAll();
    }

    @GetMapping("/{id}")
    public DossierDeces getDossierById(@PathVariable Long id) {
        return dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));
    }

    @GetMapping("/statut/{statut}")
    public List<DossierDeces> getDossiersByStatut(@PathVariable StatutDossier statut) {
        return dossierRepository.findByStatut(statut);
    }

    @PostMapping("/valider-certificat")
    public DossierDeces validerCertificat(@RequestBody Map<String, String> body) {
        String codeCadavre = body.get("codeCadavre");
        return dossierService.validerCertificatParCode(codeCadavre);
    }
}