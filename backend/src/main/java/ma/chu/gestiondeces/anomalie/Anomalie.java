package ma.chu.gestiondeces.anomalie;

import jakarta.persistence.*;
import ma.chu.gestiondeces.dossier.DossierDeces;
import ma.chu.gestiondeces.user.Role;
import ma.chu.gestiondeces.user.User;

import java.time.LocalDateTime;

@Entity
public class Anomalie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeAnomalie;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Role signaleeParRole;

    @Enumerated(EnumType.STRING)
    private StatutAnomalie statut;

    private LocalDateTime dateSignalement;

    @Column(length = 1000)
    private String actionCorrective;

    private String ancienCodeCadavre;

    private String nouveauCodeCadavre;

    private LocalDateTime dateCorrection;

    @ManyToOne
    private User corrigeePar;

    @ManyToOne
    private DossierDeces dossier;

    public Anomalie() {
    }

    public Anomalie(String typeAnomalie, String description, Role signaleeParRole, DossierDeces dossier) {
        this.typeAnomalie = typeAnomalie;
        this.description = description;
        this.signaleeParRole = signaleeParRole;
        this.dossier = dossier;
        this.statut = StatutAnomalie.OUVERTE;
        this.dateSignalement = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTypeAnomalie() {
        return typeAnomalie;
    }

    public String getDescription() {
        return description;
    }

    public Role getSignaleeParRole() {
        return signaleeParRole;
    }

    public StatutAnomalie getStatut() {
        return statut;
    }

    public LocalDateTime getDateSignalement() {
        return dateSignalement;
    }

    public String getActionCorrective() {
        return actionCorrective;
    }

    public String getAncienCodeCadavre() {
        return ancienCodeCadavre;
    }

    public String getNouveauCodeCadavre() {
        return nouveauCodeCadavre;
    }

    public LocalDateTime getDateCorrection() {
        return dateCorrection;
    }

    public User getCorrigeePar() {
        return corrigeePar;
    }

    public DossierDeces getDossier() {
        return dossier;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeAnomalie(String typeAnomalie) {
        this.typeAnomalie = typeAnomalie;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSignaleeParRole(Role signaleeParRole) {
        this.signaleeParRole = signaleeParRole;
    }

    public void setStatut(StatutAnomalie statut) {
        this.statut = statut;
    }

    public void setDateSignalement(LocalDateTime dateSignalement) {
        this.dateSignalement = dateSignalement;
    }

    public void setActionCorrective(String actionCorrective) {
        this.actionCorrective = actionCorrective;
    }

    public void setAncienCodeCadavre(String ancienCodeCadavre) {
        this.ancienCodeCadavre = ancienCodeCadavre;
    }

    public void setNouveauCodeCadavre(String nouveauCodeCadavre) {
        this.nouveauCodeCadavre = nouveauCodeCadavre;
    }

    public void setDateCorrection(LocalDateTime dateCorrection) {
        this.dateCorrection = dateCorrection;
    }

    public void setCorrigeePar(User corrigeePar) {
        this.corrigeePar = corrigeePar;
    }

    public void setDossier(DossierDeces dossier) {
        this.dossier = dossier;
    }
}