package spring_boot.deces.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot.deces.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByIpp(String ipp);
}