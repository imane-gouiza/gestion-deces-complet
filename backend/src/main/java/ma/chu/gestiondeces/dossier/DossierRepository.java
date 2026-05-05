package ma.chu.gestiondeces.dossier;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DossierRepository extends JpaRepository<DossierDeces, Long> {

    boolean existsByCodeCadavre(String codeCadavre);

    Optional<DossierDeces> findByCodeCadavre(String codeCadavre);

    List<DossierDeces> findByStatut(StatutDossier statut);
}