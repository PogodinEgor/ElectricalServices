package pogodin_egor.CRUD_TP.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pogodin_egor.CRUD_TP.models.EnergyMeasurement;

@Repository
public interface EnergyMeasurementRepository extends JpaRepository<EnergyMeasurement, Long> {
}
