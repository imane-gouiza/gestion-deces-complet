package ma.chu.gestiondeces.dossier;

import ma.chu.gestiondeces.user.User;
import jakarta.persistence.*;
import ma.chu.gestiondeces.patient.Patient;

import java.time.LocalDateTime;

@Entity
@Table(name = "dossier_deces")
public class DossierDeces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroOrdre;

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

    public DossierDeces(String numeroOrdre, Patient patient, String codeCadavre,
                        StatutDossier statut, LocalDateTime dateCreation, User creePar) {
        this.numeroOrdre = numeroOrdre;
        this.patient = patient;
        this.codeCadavre = codeCadavre;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.creePar = creePar;
    }
    @Column(name = "reference_dossier", nullable = false, unique = true)
    private String referenceDossier;

    public Long getId() {
        return id;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getCodeCadavre() {
        return codeCadavre;
    }

    public StatutDossier getStatut() {
        return statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public User getCreePar() {
        return creePar;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroOrdre(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setCodeCadavre(String codeCadavre) {
        this.codeCadavre = codeCadavre;
    }

    public void setStatut(StatutDossier statut) {
        this.statut = statut;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setCreePar(User creePar) {
        this.creePar = creePar;
    }

    public String getReferenceDossier() {
        return referenceDossier;
    }

    public void setReferenceDossier(String referenceDossier) {
        this.referenceDossier = referenceDossier;
    }
}