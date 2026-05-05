package ma.chu.gestiondeces.patient;

import jakarta.persistence.*;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipp;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String sexe;
    private String serviceOrigine;
    private String numeroAdmission;

    public Patient() {
    }

    public Patient(String ipp, String nom, String prenom, String dateNaissance, String sexe, String serviceOrigine, String numeroAdmission) {
        this.ipp = ipp;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.serviceOrigine = serviceOrigine;
        this.numeroAdmission = numeroAdmission;
    }

    public Long getId() { return id; }
    public String getIpp() { return ipp; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getDateNaissance() { return dateNaissance; }
    public String getSexe() { return sexe; }
    public String getServiceOrigine() { return serviceOrigine; }
    public String getNumeroAdmission() { return numeroAdmission; }

    public void setId(Long id) { this.id = id; }
    public void setIpp(String ipp) { this.ipp = ipp; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    public void setServiceOrigine(String serviceOrigine) { this.serviceOrigine = serviceOrigine; }
    public void setNumeroAdmission(String numeroAdmission) { this.numeroAdmission = numeroAdmission; }
}