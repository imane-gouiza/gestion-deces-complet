package ma.chu.gestiondeces.historique;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import ma.chu.gestiondeces.dossier.DossierDeces;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_actions", indexes = {
        @Index(name = "idx_historique_dossier", columnList = "dossier_id"),
        @Index(name = "idx_historique_date", columnList = "date_action")
})
public class HistoriqueAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String utilisateur;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String action;

    @Column(length = 500)
    private String description;

    @Column(name = "date_action", nullable = false)
    private LocalDateTime dateAction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id")
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler"
    })
    private DossierDeces dossier;

    public HistoriqueAction() {
    }

    public HistoriqueAction(
            String utilisateur,
            String role,
            String action,
            String description,
            LocalDateTime dateAction,
            DossierDeces dossier
    ) {
        this.utilisateur = utilisateur;
        this.role = role;
        this.action = action;
        this.description = description;
        this.dateAction = dateAction;
        this.dossier = dossier;
    }

    public Long getId() {
        return id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateAction() {
        return dateAction;
    }

    public void setDateAction(LocalDateTime dateAction) {
        this.dateAction = dateAction;
    }

    public DossierDeces getDossier() {
        return dossier;
    }

    public void setDossier(DossierDeces dossier) {
        this.dossier = dossier;
    }
}