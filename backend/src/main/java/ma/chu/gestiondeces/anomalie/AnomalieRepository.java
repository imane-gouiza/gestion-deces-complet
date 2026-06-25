package ma.chu.gestiondeces.anomalie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnomalieRepository extends JpaRepository<Anomalie, Long> {

    List<Anomalie> findByStatutOrderByDateSignalementDesc(StatutAnomalie statut);

    List<Anomalie> findAllByOrderByDateSignalementDesc();
}