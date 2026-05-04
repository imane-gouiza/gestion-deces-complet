package spring_boot.deces.repository;

import spring_boot.deces.entity.DossierDeces;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DossierDecesRepository extends JpaRepository<DossierDeces, Long> {
    boolean existsByCodeCadavre(String codeCadavre);
    List<DossierDeces> findByStatut(String statut);
}