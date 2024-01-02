package pogodin_egor.CRUD_TP.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pogodin_egor.CRUD_TP.models.TypeCounter;

import java.util.Optional;

@Repository
public interface TypeCounterRepository extends JpaRepository<TypeCounter, Long> {
    Optional<TypeCounter> findByTypeName(String typeName);
}
