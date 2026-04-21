
package spring_boot.deces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String welcome() {
        return "<h1>Bienvenue dans l'application de gestion des décès !</h1>" +
                "<p>Le serveur Java fonctionne parfaitement.</p>";
    }
}
