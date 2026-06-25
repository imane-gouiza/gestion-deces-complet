package ma.chu.gestiondeces.anomalie;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anomalies")
@CrossOrigin(origins = "http://localhost:4200")
public class AnomalieController {

    private final AnomalieService anomalieService;

    public AnomalieController(AnomalieService anomalieService) {
        this.anomalieService = anomalieService;
    }

    @PostMapping
    public Anomalie signalerAnomalie(@RequestBody CreateAnomalieRequest request) {
        return anomalieService.signalerAnomalie(request);
    }

    @GetMapping
    public List<Anomalie> getAllAnomalies() {
        return anomalieService.getAllAnomalies();
    }

    @GetMapping("/ouvertes")
    public List<Anomalie> getAnomaliesOuvertes() {
        return anomalieService.getAnomaliesOuvertes();
    }

    @PutMapping("/{id}/corriger")
    public Anomalie corrigerAnomalie(@PathVariable Long id,
                                     @RequestBody CorrigerAnomalieRequest request) {
        return anomalieService.corrigerAnomalie(id, request);
    }
}