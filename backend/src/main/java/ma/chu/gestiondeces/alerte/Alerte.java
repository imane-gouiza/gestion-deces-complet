package ma.chu.gestiondeces.alerte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import ma.chu.gestiondeces.dossier.DossierDeces;

import java.time.LocalDateTime;

@Entity
@Table(name = "alertes", indexes = {
        @Index(name = "idx_alerte_dossier", columnList = "dossier_id"),
        @Index(name = "idx_alerte_lu", columnList = "lu")
})
public class Alerte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NiveauAlerte niveau;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private boolean lu = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id")
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler"
    })
    private DossierDeces dossier;

    public Alerte() {
    }

    public Alerte(
            String message,
            NiveauAlerte niveau,
            LocalDateTime dateCreation,
            boolean lu,
            DossierDeces dossier
    ) {
        this.message = message;
        this.niveau = niveau;
        this.dateCreation = dateCreation;
        this.lu = lu;
        this.dossier = dossier;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public NiveauAlerte getNiveau() {
        return niveau;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public boolean isLu() {
        return lu;
    }

    public DossierDeces getDossier() {
        return dossier;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }
}