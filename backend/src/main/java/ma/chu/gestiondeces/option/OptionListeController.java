package ma.chu.gestiondeces.option;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/options")
@CrossOrigin(origins = "http://localhost:4200")
public class OptionListeController {

    private final OptionListeService service;

    public OptionListeController(OptionListeService service) {
        this.service = service;
    }


    @GetMapping("/{categorie}")
    public List<OptionListe> getByCategorie(@PathVariable String categorie) {
        return service.getByCategorie(categorie);
    }


    @PostMapping
    public OptionListe ajouter(@RequestBody Map<String, String> body) {
        return service.ajouter(body.get("categorie"), body.get("valeur"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.ok(Map.of("message", "Valeur supprimée avec succès"));
    }
}