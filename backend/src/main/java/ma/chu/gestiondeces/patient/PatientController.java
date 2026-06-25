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

    @GetMapping("/{ipp}")
    public Patient getPatientByIpp(@PathVariable String ipp) {
        return patientRepository.findByIpp(ipp)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));
    }

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


    private boolean matchesPatient(
            Patient p,
            String nom,
            String prenom,
            String ipp,
            String service
    ) {

        if (hasValue(nom)) {
            String nomLower = nom.toLowerCase();
            boolean nomMatch =
                    contains(p.getNom(), nomLower) ||
                            contains(p.getPrenom(), nomLower);
            if (!nomMatch) return false;
        }

        if (hasValue(prenom)) {
            if (!contains(p.getPrenom(), prenom.toLowerCase())) return false;
        }


        if (hasValue(ipp)) {
            if (!contains(p.getIpp(), ipp.toLowerCase())) return false;
        }


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
