package pogodin_egor.CRUD_TP.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pogodin_egor.CRUD_TP.models.TransformatorSubstation;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransformatorSubstationRepository extends JpaRepository<TransformatorSubstation, Long> {
    List<TransformatorSubstation> findAllByNameTransformatorSubstation(String nameTransformatorSubstation);
}
