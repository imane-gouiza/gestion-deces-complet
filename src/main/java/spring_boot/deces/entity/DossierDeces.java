package spring_boot.deces.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dossiers_deces")
public class DossierDeces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ipp;

    @Column(nullable = false, unique = true)
    private String codeCadavre;

    private String nomPatient;
    private String prenomPatient;
    private String serviceOrigine;

    @Column(nullable = false)
    private String statut;  // "Creé par BAF", "En attente de vérification", etc.

    private String anomalieMessage;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    private LocalDateTime dateValidationCertificat;
    private LocalDateTime datePriseEnCharge;
    private LocalDateTime dateArriveeMorgue;

    @ManyToOne
    @JoinColumn(name = "baf_id")
    private User baf;

    @ManyToOne
    @JoinColumn(name = "surveillant_id")
    private User surveillant;

    @ManyToOne
    @JoinColumn(name = "ambulancier_id")
    private User ambulancier;

    // Constructeurs
    public DossierDeces() {}

    public DossierDeces(String ipp, String codeCadavre, String statut) {
        this.ipp = ipp;
        this.codeCadavre = codeCadavre;
        this.statut = statut;
        this.dateCreation = LocalDateTime.now();
    }

    // Getters et Setters (tous)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIpp() { return ipp; }
    public void setIpp(String ipp) { this.ipp = ipp; }

    public String getCodeCadavre() { return codeCadavre; }
    public void setCodeCadavre(String codeCadavre) { this.codeCadavre = codeCadavre; }

    public String getNomPatient() { return nomPatient; }
    public void setNomPatient(String nomPatient) { this.nomPatient = nomPatient; }

    public String getPrenomPatient() { return prenomPatient; }
    public void setPrenomPatient(String prenomPatient) { this.prenomPatient = prenomPatient; }

    public String getServiceOrigine() { return serviceOrigine; }
    public void setServiceOrigine(String serviceOrigine) { this.serviceOrigine = serviceOrigine; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getAnomalieMessage() { return anomalieMessage; }
    public void setAnomalieMessage(String anomalieMessage) { this.anomalieMessage = anomalieMessage; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public LocalDateTime getDateValidationCertificat() { return dateValidationCertificat; }
    public void setDateValidationCertificat(LocalDateTime dateValidationCertificat) { this.dateValidationCertificat = dateValidationCertificat; }

    public LocalDateTime getDatePriseEnCharge() { return datePriseEnCharge; }
    public void setDatePriseEnCharge(LocalDateTime datePriseEnCharge) { this.datePriseEnCharge = datePriseEnCharge; }

    public LocalDateTime getDateArriveeMorgue() { return dateArriveeMorgue; }
    public void setDateArriveeMorgue(LocalDateTime dateArriveeMorgue) { this.dateArriveeMorgue = dateArriveeMorgue; }

    public User getBaf() { return baf; }
    public void setBaf(User baf) { this.baf = baf; }

    public User getSurveillant() { return surveillant; }
    public void setSurveillant(User surveillant) { this.surveillant = surveillant; }

    public User getAmbulancier() { return ambulancier; }
    public void setAmbulancier(User ambulancier) { this.ambulancier = ambulancier; }
}