package spring_boot.deces.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ipp;

    private String nom;
    private String prenom;
    private String serviceOrigine;
    private LocalDate dateNaissance;

    // Constructeurs, getters et setters
    public Patient() {}

    // Getters et setters (génère-les via ton IDE ou manuellement)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIpp() { return ipp; }
    public void setIpp(String ipp) { this.ipp = ipp; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getServiceOrigine() { return serviceOrigine; }
    public void setServiceOrigine(String serviceOrigine) { this.serviceOrigine = serviceOrigine; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
}