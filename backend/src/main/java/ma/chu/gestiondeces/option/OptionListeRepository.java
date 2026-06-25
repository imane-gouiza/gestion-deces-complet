package ma.chu.gestiondeces.option;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionListeRepository extends JpaRepository<OptionListe, Long> {

    List<OptionListe> findByCategorieOrderByValeurAsc(String categorie);

    boolean existsByCategorieAndValeur(String categorie, String valeur);
}