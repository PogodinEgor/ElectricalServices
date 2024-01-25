package pogodin_egor.CRUD_TP.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pogodin_egor.CRUD_TP.models.EnergyCounter;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnergyCounterRepository extends JpaRepository<EnergyCounter, Long> {


   Page<EnergyCounter> findBySerialNumber(String serialNumber, Pageable pageable);

   Page<EnergyCounter> findByEnergyMeasurements(EnergyCounter energyCounter, Pageable pageable);


}
