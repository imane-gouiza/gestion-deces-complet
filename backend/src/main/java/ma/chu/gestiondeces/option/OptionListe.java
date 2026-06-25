package ma.chu.gestiondeces.option;

import jakarta.persistence.*;


@Entity
@Table(
        name = "options_liste",
        uniqueConstraints = @UniqueConstraint(columnNames = {"categorie", "valeur"})
)
public class OptionListe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categorie;

    @Column(nullable = false)
    private String valeur;

    public OptionListe() {
    }

    public OptionListe(String categorie, String valeur) {
        this.categorie = categorie;
        this.valeur = valeur;
    }

    public Long getId() {
        return id;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getValeur() {
        return valeur;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}