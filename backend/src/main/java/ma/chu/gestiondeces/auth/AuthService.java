package ma.chu.gestiondeces.auth;

import ma.chu.gestiondeces.user.User;
import ma.chu.gestiondeces.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        return new LoginResponse(
                user.getId(),
                user.getNom(),
                user.getEmail(),
                user.getRole(),
                "demo-token-" + user.getRole()
        );
    }
}