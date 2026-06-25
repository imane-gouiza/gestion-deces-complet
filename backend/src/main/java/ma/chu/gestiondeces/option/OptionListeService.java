package ma.chu.gestiondeces.option;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionListeService {

    private final OptionListeRepository repository;

    public OptionListeService(OptionListeRepository repository) {
        this.repository = repository;
    }


    public List<OptionListe> getByCategorie(String categorie) {
        return repository.findByCategorieOrderByValeurAsc(categorie);
    }

    public OptionListe ajouter(String categorie, String valeur) {

        if (categorie == null || categorie.trim().isEmpty()) {
            throw new RuntimeException("La catégorie est obligatoire");
        }
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new RuntimeException("La valeur est obligatoire");
        }

        String cat = categorie.trim();
        String val = valeur.trim();

        if (repository.existsByCategorieAndValeur(cat, val)) {
            throw new RuntimeException("Cette valeur existe déjà dans la liste");
        }

        return repository.save(new OptionListe(cat, val));
    }


    public void supprimer(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Valeur introuvable");
        }
        repository.deleteById(id);
    }
}