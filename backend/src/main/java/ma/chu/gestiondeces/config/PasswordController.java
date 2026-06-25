package ma.chu.gestiondeces.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tools")
public class PasswordController {

    private final PasswordEncoder passwordEncoder;

    public PasswordController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/encode/{password}")
    public String encode(@PathVariable String password) {
        return passwordEncoder.encode(password);
    }
}