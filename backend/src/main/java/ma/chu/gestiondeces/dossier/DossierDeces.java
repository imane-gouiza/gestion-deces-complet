package ma.chu.gestiondeces.dossier;

import jakarta.persistence.*;
import ma.chu.gestiondeces.patient.Patient;
import ma.chu.gestiondeces.user.User;

import java.time.LocalDateTime;

@Entity
public class DossierDeces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referenceDossier;

    @ManyToOne
    private Patient patient;

    private String codeCadavre;

    @Enumerated(EnumType.STRING)
    private StatutDossier statut;

    private LocalDateTime dateCreation;

    @ManyToOne
    private User creePar;

    public DossierDeces() {
    }

    public DossierDeces(String referenceDossier, Patient patient, String codeCadavre, StatutDossier statut, LocalDateTime dateCreation, User creePar) {
        this.referenceDossier = referenceDossier;
        this.patient = patient;
        this.codeCadavre = codeCadavre;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.creePar = creePar;
    }

    public Long getId() { return id; }
    public String getReferenceDossier() { return referenceDossier; }
    public Patient getPatient() { return patient; }
    public String getCodeCadavre() { return codeCadavre; }
    public StatutDossier getStatut() { return statut; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public User getCreePar() { return creePar; }

    public void setId(Long id) { this.id = id; }
    public void setReferenceDossier(String referenceDossier) { this.referenceDossier = referenceDossier; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setCodeCadavre(String codeCadavre) { this.codeCadavre = codeCadavre; }
    public void setStatut(StatutDossier statut) { this.statut = statut; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public void setCreePar(User creePar) { this.creePar = creePar; }
}
