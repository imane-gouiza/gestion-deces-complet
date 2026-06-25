package spring_boot.deces.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot.deces.entity.Historique;
import java.util.List;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

    List<Historique> findByDossierDecesId(Long id);

}