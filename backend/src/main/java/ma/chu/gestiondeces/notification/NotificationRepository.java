package ma.chu.gestiondeces.notification;

import ma.chu.gestiondeces.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRoleDestinataireOrderByDateCreationDesc(Role roleDestinataire);

    long countByRoleDestinataireAndLueFalse(Role roleDestinataire);
}