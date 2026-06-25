package spring_boot.deces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot.deces.dto.LoginRequest;
import spring_boot.deces.dto.LoginResponse;
import spring_boot.deces.entity.User;
import spring_boot.deces.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            // Correspond au constructeur : message, role (null), success (false)
            return ResponseEntity.status(401).body(new LoginResponse("Utilisateur introuvable", null, false));
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body(new LoginResponse("Mot de passe incorrect", null, false));
        }

        // Au lieu de user.getRole(), on utilise user.getRole().name()
        return ResponseEntity.ok(new LoginResponse("Success", user.getRole().name(), true));
    }
}