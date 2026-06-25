package ma.chu.gestiondeces.historique;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueActionRepository extends JpaRepository<HistoriqueAction, Long> {

    List<HistoriqueAction> findByDossierIdOrderByDateActionDesc(Long dossierId);

    List<HistoriqueAction> findAllByOrderByDateActionDesc();

    long countByAction(String action);
}