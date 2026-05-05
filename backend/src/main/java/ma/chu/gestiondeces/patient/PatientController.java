package ma.chu.gestiondeces.patient;

import org.springframework.web.bind.annotation.*;

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
}