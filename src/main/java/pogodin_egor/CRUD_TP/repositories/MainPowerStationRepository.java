package pogodin_egor.CRUD_TP.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pogodin_egor.CRUD_TP.models.MainPowerStation;

import java.util.Optional;


@Repository
public interface MainPowerStationRepository extends JpaRepository<MainPowerStation, Long> {
    Optional<MainPowerStation> findByNamePowerStation(String namePowerStation);
}
