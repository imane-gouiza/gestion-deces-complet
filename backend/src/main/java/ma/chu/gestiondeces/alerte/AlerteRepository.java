package ma.chu.gestiondeces.alerte;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AlerteRepository extends JpaRepository<Alerte, Long> {

    @Query("""
           SELECT a
           FROM Alerte a
           WHERE a.lu = false
           ORDER BY a.dateCreation DESC
           """)
    List<Alerte> findNonLues();

    @Query("""
           SELECT a
           FROM Alerte a
           ORDER BY a.dateCreation DESC
           """)
    List<Alerte> findAllOrderByDateCreationDesc();

    @Query("""
           SELECT a
           FROM Alerte a
           WHERE a.dossier.id = :dossierId
           ORDER BY a.dateCreation DESC
           """)
    List<Alerte> findByDossierId(@Param("dossierId") Long dossierId);

    long countByLuFalse();

    long countByNiveauAndLuFalse(NiveauAlerte niveau);

    @Query("""
           SELECT COUNT(a)
           FROM Alerte a
           WHERE a.dossier.id = :dossierId
           AND a.niveau = :niveau
           AND a.lu = false
           """)
    long countAlertesActivesForDossier(
            @Param("dossierId") Long dossierId,
            @Param("niveau") NiveauAlerte niveau
    );
}
