package pogodin_egor.CRUD_TP.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pogodin_egor.CRUD_TP.models.MainPowerStation;
import pogodin_egor.CRUD_TP.models.TransformatorSubstation;
import pogodin_egor.CRUD_TP.repositories.MainPowerStationRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MainPowerStationService {

    @Autowired
    private MainPowerStationRepository mainPowerStationRepository;

    public List<MainPowerStation> findAll(){return mainPowerStationRepository.findAll();}


    public MainPowerStation findOne(long id){
        return mainPowerStationRepository.findById(id).orElse(null);
    }

    public Optional<MainPowerStation> findByNamePowerStation(String namePowerStation){
      return   mainPowerStationRepository.findByNamePowerStation(namePowerStation);
    }

    @Transactional
    public void save(MainPowerStation mainPowerStation) {
        mainPowerStationRepository.save(mainPowerStation);
    }

    @Transactional
    public void update(long id, MainPowerStation updatedMainPowerStation) {
        MainPowerStation mainPowerStationToBeUpdated = mainPowerStationRepository.findById(id).get();
        updatedMainPowerStation.setId(id);

        mainPowerStationRepository.save(updatedMainPowerStation);
    }

    @Transactional
    public void delete(long id) {
        mainPowerStationRepository.deleteById(id);
    }
}
