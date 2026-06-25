package spring_boot.deces.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique")
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dossier_deces_id")
    private DossierDeces dossierDeces;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private User utilisateur;

    private String action;
    private LocalDateTime dateAction;
    private String details;

    // Constructeurs, getters et setters
    public Historique() {}

    public Historique(DossierDeces dossierDeces, User utilisateur, String action, String details) {
        this.dossierDeces = dossierDeces;
        this.utilisateur = utilisateur;
        this.action = action;
        this.dateAction = LocalDateTime.now();
        this.details = details;
    }

    // Getters et setters...
}