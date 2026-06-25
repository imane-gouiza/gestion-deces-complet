/*package ma.chu.gestiondeces.integration;

import ma.chu.gestiondeces.patient.Patient;
import org.springframework.stereotype.Service;

@Service
public class OsiIntegrationService {

    private final Hl7Client hl7Client;
    private final Hl7Parser hl7Parser;

    public OsiIntegrationService(
            Hl7Client hl7Client,
            Hl7Parser hl7Parser
    ) {
        this.hl7Client = hl7Client;
        this.hl7Parser = hl7Parser;
    }

    public Patient rechercherPatientDepuisOSI(
            String ipp
    ) throws Exception {

        String hl7Message =
                "MSH|^~\\&|GESTION_DECES|CHU|OSI|CHU||QRY^A19|"
                        + ipp;

        String response =
                hl7Client.sendMessage(
                        hl7Message
                );

        return hl7Parser.parsePatient(
                response
        );
    }
}*/