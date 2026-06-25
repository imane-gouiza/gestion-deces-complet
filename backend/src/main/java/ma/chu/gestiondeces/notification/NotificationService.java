package ma.chu.gestiondeces.notification;

import ma.chu.gestiondeces.dossier.DossierDeces;
import ma.chu.gestiondeces.user.Role;
import ma.chu.gestiondeces.user.User;
import ma.chu.gestiondeces.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository,
            EmailService emailService
    ) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public Notification creerNotification(
            String titre,
            String message,
            Role roleDestinataire,
            DossierDeces dossier
    ) {

        Notification notification = new Notification(
                titre,
                message,
                roleDestinataire,
                dossier
        );

        Notification saved = notificationRepository.save(notification);


        List<User> users = userRepository.findByRole(roleDestinataire);

        if (users.isEmpty()) {
            System.out.println("⚠ Aucun utilisateur trouvé pour le rôle : " + roleDestinataire);
        }

        for (User user : users) {
            try {
                System.out.println("📧 Envoi email à : " + user.getEmail());
                emailService.envoyerEmail(
                        user.getEmail(),
                        titre,
                        message
                );
                System.out.println("✅ Email envoyé avec succès à : " + user.getEmail());
            } catch (Exception e) {
                System.out.println("❌ Erreur email pour " + user.getEmail() + " : " + e.getMessage());
                e.printStackTrace();
            }
        }

        return saved;
    }

    public List<Notification> getNotificationsByRole(Role role) {
        return notificationRepository
                .findByRoleDestinataireOrderByDateCreationDesc(role);
    }

    public long countNonLues(Role role) {
        return notificationRepository
                .countByRoleDestinataireAndLueFalse(role);
    }

    public Notification marquerCommeLue(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));
        notification.setLue(true);
        return notificationRepository.save(notification);
    }
}