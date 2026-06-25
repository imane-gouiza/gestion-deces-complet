package ma.chu.gestiondeces.dossier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DossierRepository extends JpaRepository<DossierDeces, Long> {

    boolean existsByCodeCadavre(String codeCadavre);

    Optional<DossierDeces> findByCodeCadavre(String codeCadavre);

    List<DossierDeces> findByStatut(StatutDossier statut);

    long countByStatut(StatutDossier statut);

    @Query("SELECT COUNT(d) FROM DossierDeces d WHERE d.patient.ipp = :ipp")
    long countDossiersByPatientIpp(@Param("ipp") String ipp);

    @Query("SELECT COUNT(d) FROM DossierDeces d WHERE YEAR(d.dateCreation) = :annee")
    long countByAnnee(@Param("annee") int annee);
}