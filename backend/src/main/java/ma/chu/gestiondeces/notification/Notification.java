package ma.chu.gestiondeces.notification;

import jakarta.persistence.*;
import ma.chu.gestiondeces.dossier.DossierDeces;
import ma.chu.gestiondeces.user.Role;

import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;


    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private Role roleDestinataire;

    private boolean lue = false;

    private LocalDateTime dateCreation;

    @ManyToOne
    private DossierDeces dossier;

    public Notification() {}

    public Notification(String titre, String message, Role roleDestinataire, DossierDeces dossier) {
        this.titre = titre;
        this.message = message;
        this.roleDestinataire = roleDestinataire;
        this.dossier = dossier;
        this.dateCreation = LocalDateTime.now();
        this.lue = false;
    }

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public String getMessage() { return message; }
    public Role getRoleDestinataire() { return roleDestinataire; }
    public boolean isLue() { return lue; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public DossierDeces getDossier() { return dossier; }

    public void setId(Long id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setMessage(String message) { this.message = message; }
    public void setRoleDestinataire(Role roleDestinataire) { this.roleDestinataire = roleDestinataire; }
    public void setLue(boolean lue) { this.lue = lue; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public void setDossier(DossierDeces dossier) { this.dossier = dossier; }
}