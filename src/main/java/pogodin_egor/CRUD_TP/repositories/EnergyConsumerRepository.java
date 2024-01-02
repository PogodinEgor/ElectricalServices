package pogodin_egor.CRUD_TP.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pogodin_egor.CRUD_TP.models.EnergyConsumer;


@Repository
public interface EnergyConsumerRepository extends JpaRepository<EnergyConsumer, Long> {
   Page<EnergyConsumer> findByLastNameIgnoreCase(String lastName, Pageable pageable);



}
