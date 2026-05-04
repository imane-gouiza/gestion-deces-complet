package spring_boot.deces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot.deces.entity.Patient;
import spring_boot.deces.repository.PatientRepository;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/{ipp}")
    public ResponseEntity<?> getPatientByIpp(@PathVariable String ipp) {
        Patient patient = patientRepository.findByIpp(ipp);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }
}