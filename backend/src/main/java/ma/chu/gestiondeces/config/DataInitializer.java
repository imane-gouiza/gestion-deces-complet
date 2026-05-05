package ma.chu.gestiondeces.config;

import ma.chu.gestiondeces.patient.Patient;
import ma.chu.gestiondeces.patient.PatientRepository;
import ma.chu.gestiondeces.user.Role;
import ma.chu.gestiondeces.user.User;
import ma.chu.gestiondeces.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public DataInitializer(UserRepository userRepository, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new User("Agent BAF", "baf@chu.ma", "baf123", Role.BAF));
            userRepository.save(new User("Surveillant Général", "surveillant@chu.ma", "surveillant123", Role.SURVEILLANT_GENERAL));
            userRepository.save(new User("Ambulancier", "ambulancier@chu.ma", "ambulancier123", Role.AMBULANCIER));
        }

        if (patientRepository.count() == 0) {
            patientRepository.save(new Patient("IP001", "Benali", "Ahmed", "1970-04-12", "Homme", "Urgences", "ADM-001"));
            patientRepository.save(new Patient("IP002", "El Amrani", "Fatima", "1965-09-25", "Femme", "Réanimation", "ADM-002"));
            patientRepository.save(new Patient("IP003", "Alaoui", "Mohamed", "1958-01-18", "Homme", "Cardiologie", "ADM-003"));
        }
    }
}