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

    public DossierController(DossierService dossierService,
                             DossierRepository dossierRepository) {
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

    @GetMapping("/code/{codeCadavre}")
    public DossierDeces getDossierByCodeCadavre(@PathVariable String codeCadavre) {
        return dossierRepository.findByCodeCadavre(codeCadavre)
                .orElseThrow(() -> new RuntimeException("Aucun dossier trouvé pour ce code cadavre"));
    }

    @PostMapping("/valider-certificat")
    public DossierDeces validerCertificat(@RequestBody Map<String, String> body) {
        return dossierService.validerCertificatParCode(body.get("codeCadavre"));
    }

    @PostMapping("/prise-en-charge")
    public DossierDeces confirmerPriseEnCharge(@RequestBody Map<String, String> body) {
        return dossierService.confirmerPriseEnCharge(body.get("codeCadavre"));
    }

    @PostMapping("/arrivee-morgue")
    public DossierDeces confirmerArriveeMorgue(@RequestBody Map<String, String> body) {
        return dossierService.confirmerArriveeMorgue(body.get("codeCadavre"));
    }

    /**
     * Recherche avancée multi-critères sur les dossiers décès.
     * GET /api/dossiers/search
     *
     * Paramètres (tous optionnels) :
     *   q           — Terme global cherché dans numeroOrdre, codeCadavre,
     *                 patient.ipp, patient.nom, patient.prenom, patient.serviceOrigine
     *   numeroOrdre — Filtrage partiel sur le numéro d'ordre
     *   ipp         — Filtrage partiel sur l'IPP du patient
     *   nom         — Filtrage partiel sur le nom OU prénom du patient
     *   codeCadavre — Filtrage partiel sur le code cadavre
     *   service     — Filtrage partiel sur le service d'origine du patient
     *   statut      — Filtrage exact sur le statut (ex : EN_ATTENTE_VERIFICATION)
     *
     * Les critères individuels sont combinés en AND.
     * Le terme global `q` est évalué en OR sur tous les champs textuels.
     * Si aucun critère n'est fourni, tous les dossiers sont retournés triés
     * par date de création décroissante.
     */
    @GetMapping("/search")
    public List<DossierDeces> searchDossiers(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String numeroOrdre,
            @RequestParam(required = false) String ipp,
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String codeCadavre,
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String statut
    ) {
        return dossierService.searchDossiers(
                q, numeroOrdre, ipp, nom, codeCadavre, service, statut
        );
    }
}
