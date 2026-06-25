package ma.chu.gestiondeces.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ── Lecture ─────────────────────────────────────────────────────────────

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getUsersByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return UserResponse.fromEntity(user);
    }

    // ── Création ────────────────────────────────────────────────────────────

    public UserResponse createUser(CreateUserRequest request) {

        if (request.getNom() == null || request.getNom().trim().isEmpty()) {
            throw new RuntimeException("Le nom est obligatoire");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("L'email est obligatoire");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Le mot de passe est obligatoire");
        }
        if (request.getRole() == null) {
            throw new RuntimeException("Le rôle est obligatoire");
        }

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        User user = new User(
                request.getNom().trim(),
                email,
                request.getPassword(),
                request.getRole()
        );

        return UserResponse.fromEntity(userRepository.save(user));
    }

    // ── Modification ────────────────────────────────────────────────────────

    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (request.getNom() != null && !request.getNom().trim().isEmpty()) {
            user.setNom(request.getNom().trim());
        }

        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            String newEmail = request.getEmail().trim().toLowerCase();
            // Vérifier l'unicité uniquement si l'email change
            if (!newEmail.equals(user.getEmail()) && userRepository.existsByEmail(newEmail)) {
                throw new RuntimeException("Cet email est déjà utilisé");
            }
            user.setEmail(newEmail);
        }

        // Mot de passe optionnel : on ne le change que s'il est fourni
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPassword(request.getPassword());
        }

        if (request.getRole() != null) {
            // Empêcher de retirer le rôle ADMINISTRATION au dernier admin
            if (user.getRole() == Role.ADMINISTRATION
                    && request.getRole() != Role.ADMINISTRATION
                    && userRepository.countByRole(Role.ADMINISTRATION) <= 1) {
                throw new RuntimeException("Impossible de modifier le rôle du dernier administrateur");
            }
            user.setRole(request.getRole());
        }

        return UserResponse.fromEntity(userRepository.save(user));
    }

    // ── Suppression ─────────────────────────────────────────────────────────

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Protection : ne jamais supprimer le dernier administrateur
        if (user.getRole() == Role.ADMINISTRATION
                && userRepository.countByRole(Role.ADMINISTRATION) <= 1) {
            throw new RuntimeException("Impossible de supprimer le dernier administrateur");
        }

        userRepository.delete(user);
    }
}