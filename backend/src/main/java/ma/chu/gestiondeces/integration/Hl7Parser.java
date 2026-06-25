/*package ma.chu.gestiondeces.integration;

import ma.chu.gestiondeces.patient.Patient;
import org.springframework.stereotype.Component;

@Component
public class Hl7Parser {

    public Patient parsePatient(
            String response
    ) {

        String[] fields =
                response.split("\\|");

        Patient patient =
                new Patient();

        patient.setIpp(fields[1]);
        patient.setNom(fields[2]);
        patient.setPrenom(fields[3]);

        return patient;
    }
}*/