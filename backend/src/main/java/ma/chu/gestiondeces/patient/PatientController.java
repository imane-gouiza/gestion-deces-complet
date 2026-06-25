package ma.chu.gestiondeces.patient;

import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Récupérer un patient par IPP exact.
     * GET /api/patients/{ipp}
     */
    @GetMapping("/{ipp}")
    public Patient getPatientByIpp(@PathVariable String ipp) {
        return patientRepository.findByIpp(ipp)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));
    }

    /**
     * Recherche avancée multi-critères.
     * GET /api/patients/search?nom=...&prenom=...&ipp=...&service=...
     *
     * Tous les paramètres sont optionnels.
     * La recherche est insensible à la casse et fonctionne en "contains".
     * - nom    : cherché dans patient.nom ET patient.prenom (OR)
     * - prenom : cherché dans patient.prenom uniquement
     * - ipp    : cherché dans patient.ipp
     * - service: cherché dans patient.serviceOrigine
     * Les critères fournis sont combinés en AND.
     * Si aucun critère n'est fourni, tous les patients sont retournés.
     */
    @GetMapping("/search")
    public List<Patient> searchPatients(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String ipp,
            @RequestParam(required = false) String service
    ) {
        List<Patient> all = patientRepository.findAll();

        return all.stream()
                .filter(p -> matchesPatient(p, nom, prenom, ipp, service))
                .sorted(Comparator.comparing(
                        p -> p.getNom() != null ? p.getNom() : "",
                        Comparator.naturalOrder()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Vérifie si un patient correspond aux critères de recherche.
     * Tous les critères non-renseignés (null ou vide) sont ignorés.
     */
    private boolean matchesPatient(
            Patient p,
            String nom,
            String prenom,
            String ipp,
            String service
    ) {
        // Filtre nom : cherche dans nom ET prenom (OR)
        if (hasValue(nom)) {
            String nomLower = nom.toLowerCase();
            boolean nomMatch =
                    contains(p.getNom(), nomLower) ||
                            contains(p.getPrenom(), nomLower);
            if (!nomMatch) return false;
        }

        // Filtre prenom : cherche uniquement dans prenom
        if (hasValue(prenom)) {
            if (!contains(p.getPrenom(), prenom.toLowerCase())) return false;
        }

        // Filtre IPP
        if (hasValue(ipp)) {
            if (!contains(p.getIpp(), ipp.toLowerCase())) return false;
        }

        // Filtre service
        if (hasValue(service)) {
            if (!contains(p.getServiceOrigine(), service.toLowerCase())) return false;
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
