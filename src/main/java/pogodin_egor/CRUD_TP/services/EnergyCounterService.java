package pogodin_egor.CRUD_TP.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pogodin_egor.CRUD_TP.models.EnergyConsumer;
import pogodin_egor.CRUD_TP.models.EnergyCounter;
import pogodin_egor.CRUD_TP.models.PowerLine;
import pogodin_egor.CRUD_TP.models.TypeCounter;
import pogodin_egor.CRUD_TP.repositories.EnergyConsumerRepository;
import pogodin_egor.CRUD_TP.repositories.EnergyCounterRepository;
import pogodin_egor.CRUD_TP.repositories.PowerLineRepository;
import pogodin_egor.CRUD_TP.repositories.TypeCounterRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EnergyCounterService {

    @Autowired
    private EnergyCounterRepository energyCounterRepository;

    @Autowired
    private PowerLineRepository powerLineRepository;

    @Autowired
    private TypeCounterRepository typeCounterRepository;

    @Autowired
    private EnergyConsumerRepository energyConsumerRepository;

    public List<EnergyCounter> findAll(){
        return energyCounterRepository.findAll();
    }


    public Page<EnergyCounter> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("serialNumber"));
        return energyCounterRepository.findAll(pageRequest);
    }

    public EnergyCounter findOne(long id){
        return energyCounterRepository.findById(id).orElse(null);
    }

    public Page<EnergyCounter> findBySerialNumber(String serialNumber, Pageable pageable){
        return energyCounterRepository.findBySerialNumber(serialNumber, pageable);
    }

    public Page<EnergyCounter> findByEnergyMeasurements(long id, Pageable pageable){
        return energyCounterRepository.findByEnergyMeasurements(energyCounterRepository.findById(id).get(), pageable);
    }

    @Transactional
    public void save(EnergyCounter energyCounter) {
        energyCounterRepository.save(energyCounter);
    }

    @Transactional
    public void update(long id, EnergyCounter updatedCounter) {
        Optional<EnergyCounter> energyCounterToBeUpdated = energyCounterRepository.findById(id);
        Optional<PowerLine> powerLine = powerLineRepository.findById(updatedCounter.getLine().getId());
        Optional<TypeCounter> typeCounter = typeCounterRepository.findById(updatedCounter.getCounterType().getId());
        Optional<EnergyConsumer> energyConsumer = energyConsumerRepository.findById(updatedCounter.getEnergyConsumer().getId());
        if(energyCounterToBeUpdated.isPresent()){
            EnergyCounter existingCounter = energyCounterToBeUpdated.get();

            existingCounter.setCounterType(typeCounter.get());
            existingCounter.setManufacturingYear(updatedCounter.getManufacturingYear());
            existingCounter.setSerialNumber(updatedCounter.getSerialNumber());
            existingCounter.setAmperage(updatedCounter.getAmperage());
            existingCounter.setVoltage(updatedCounter.getVoltage());
            existingCounter.setGearRatio(updatedCounter.getGearRatio());
            existingCounter.setAccuracyClass(updatedCounter.getAccuracyClass());
            existingCounter.setBitDepth(updatedCounter.getBitDepth());
            existingCounter.setQuarterVerification(updatedCounter.getQuarterVerification());
            existingCounter.setCalibrationInterval(updatedCounter.getCalibrationInterval());

            existingCounter.setLine(powerLine.get());
            existingCounter.setEnergyConsumer(energyConsumer.get());
            energyCounterRepository.save(existingCounter);
        }else {
            throw new IllegalArgumentException("EnergyCounter not found with id: " + id);
        }

    }


    @Transactional
    public void delete(long id) {
        energyCounterRepository.deleteById(id);
    }
}
