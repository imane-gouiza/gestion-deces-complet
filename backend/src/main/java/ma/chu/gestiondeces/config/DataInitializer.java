package ma.chu.gestiondeces.config;

import ma.chu.gestiondeces.option.OptionListe;
import ma.chu.gestiondeces.option.OptionListeRepository;
import ma.chu.gestiondeces.patient.Patient;
import ma.chu.gestiondeces.patient.PatientRepository;
import ma.chu.gestiondeces.user.Role;
import ma.chu.gestiondeces.user.User;
import ma.chu.gestiondeces.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class  DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final OptionListeRepository optionListeRepository;

    public DataInitializer(UserRepository userRepository,
                           PatientRepository patientRepository,
                           OptionListeRepository optionListeRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.optionListeRepository = optionListeRepository;
    }

    @Override
    public void run(String... args) {


        if (userRepository.count() == 0) {
            userRepository.save(new User("Agent BAF", "baf@chu.ma", "baf123", Role.BAF));
            userRepository.save(new User("Surveillant Général", "surveillant@chu.ma", "surveillant123", Role.SURVEILLANT_GENERAL));
            userRepository.save(new User("Ambulancier", "ambulancier@chu.ma", "ambulancier123", Role.AMBULANCIER));
            userRepository.save(new User("Administrateur", "admin@chu.ma", "admin123", Role.ADMINISTRATION));
        }

        if (patientRepository.count() == 0) {
            patientRepository.save(new Patient("IP001", "Benali", "Ahmed", "1970-04-12", "Homme", "Urgences", "ADM-001"));
            patientRepository.save(new Patient("IP002", "El Amrani", "Fatima", "1965-09-25", "Femme", "Réanimation", "ADM-002"));
            patientRepository.save(new Patient("IP003", "Alaoui", "Mohamed", "1958-01-18", "Homme", "Cardiologie", "ADM-003"));
        }


        if (optionListeRepository.count() == 0) {
            optionListeRepository.save(new OptionListe("TYPE_ANOMALIE", "Code introuvable"));
            optionListeRepository.save(new OptionListe("TYPE_ANOMALIE", "Code incorrect"));
            optionListeRepository.save(new OptionListe("TYPE_ANOMALIE", "Informations incohérentes"));
            optionListeRepository.save(new OptionListe("TYPE_ANOMALIE", "Dossier non conforme"));
            optionListeRepository.save(new OptionListe("TYPE_ANOMALIE", "Erreur de scan"));
            optionListeRepository.save(new OptionListe("TYPE_ANOMALIE", "Autre"));

            optionListeRepository.save(new OptionListe("STATUT_ANOMALIE", "OUVERTE"));
            optionListeRepository.save(new OptionListe("STATUT_ANOMALIE", "CORRIGEE"));

            optionListeRepository.save(new OptionListe("ROLE_SIGNALEMENT", "SURVEILLANT_GENERAL"));
            optionListeRepository.save(new OptionListe("ROLE_SIGNALEMENT", "AMBULANCIER"));
        }


        if (optionListeRepository.findByCategorieOrderByValeurAsc("SERVICE").isEmpty()) {
            optionListeRepository.save(new OptionListe("SERVICE", "Urgences"));
            optionListeRepository.save(new OptionListe("SERVICE", "Réanimation"));
            optionListeRepository.save(new OptionListe("SERVICE", "Cardiologie"));
            optionListeRepository.save(new OptionListe("SERVICE", "Pédiatrie"));
            optionListeRepository.save(new OptionListe("SERVICE", "Chirurgie"));
            optionListeRepository.save(new OptionListe("SERVICE", "Médecine interne"));
            optionListeRepository.save(new OptionListe("SERVICE", "Oncologie"));
        }
    }
}