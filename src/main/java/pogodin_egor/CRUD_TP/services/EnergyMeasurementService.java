package pogodin_egor.CRUD_TP.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pogodin_egor.CRUD_TP.models.EnergyCounter;
import pogodin_egor.CRUD_TP.models.EnergyMeasurement;
import pogodin_egor.CRUD_TP.repositories.EnergyCounterRepository;
import pogodin_egor.CRUD_TP.repositories.EnergyMeasurementRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EnergyMeasurementService {

    @Autowired
    private EnergyMeasurementRepository energyMeasurementRepository;

    @Autowired
    private EnergyCounterRepository energyCounterRepository;

    public List<EnergyMeasurement> findAll() {return energyMeasurementRepository.findAll();}

    public Page<EnergyMeasurement> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("meterReadings"));
        return energyMeasurementRepository.findAll(pageRequest);
    }

    public EnergyMeasurement findOne(long id) {
        return energyMeasurementRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(EnergyMeasurement energyMeasurement) {
        energyMeasurementRepository.save(energyMeasurement);
    }

    @Transactional
    public void update(long id, EnergyMeasurement updatedMeasurement) {
        Optional<EnergyMeasurement> energyMeasurementToBeUpdated = energyMeasurementRepository.findById(id);

        if (energyMeasurementToBeUpdated.isPresent()) {
            EnergyMeasurement existingMeasurement = energyMeasurementToBeUpdated.get();

            existingMeasurement.setMeterReadings(updatedMeasurement.getMeterReadings());
            existingMeasurement.setDate(updatedMeasurement.getDate());
            existingMeasurement.setComment(updatedMeasurement.getComment());
//            existingMeasurement.setCounter(updatedMeasurement.getCounter());
            if (updatedMeasurement.getCounter() != null) {
                EnergyCounter counter = energyCounterRepository.findById(updatedMeasurement.getCounter().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Counter not found"));
                existingMeasurement.setCounter(counter);
            }

            energyMeasurementRepository.save(existingMeasurement);
        } else {
            throw new IllegalArgumentException("EnergyMeasurement not found with id: " + id);
        }


    }

    @Transactional
    public void delete(long id) {
        energyMeasurementRepository.deleteById(id);
    }
}
