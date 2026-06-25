package ma.chu.gestiondeces.auth;

import ma.chu.gestiondeces.user.User;
import ma.chu.gestiondeces.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import ma.chu.gestiondeces.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public LoginResponse login(LoginRequest request) {

        System.out.println("================================");
        System.out.println("EMAIL = [" + request.getEmail() + "]");
        System.out.println("PASSWORD = [" + request.getPassword() + "]");
        System.out.println("================================");

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        System.out.println("USER TROUVE = " + user.getEmail());
        System.out.println("PASS BD = [" + user.getPassword() + "]");

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                user.getId(),
                user.getNom(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}