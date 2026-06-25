package ma.chu.gestiondeces.notification;

import ma.chu.gestiondeces.user.Role;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/role/{role}")
    public List<Notification> getNotificationsByRole(@PathVariable Role role) {
        return notificationService.getNotificationsByRole(role);
    }

    @GetMapping("/role/{role}/non-lues/count")
    public Map<String, Long> countNonLues(@PathVariable Role role) {
        return Map.of("count", notificationService.countNonLues(role));
    }

    @PutMapping("/{id}/lire")
    public Notification marquerCommeLue(@PathVariable Long id) {
        return notificationService.marquerCommeLue(id);
    }
}