package pogodin_egor.CRUD_TP.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pogodin_egor.CRUD_TP.exception.DublicateNameException;
import pogodin_egor.CRUD_TP.models.PowerLine;

import pogodin_egor.CRUD_TP.repositories.PowerLineRepository;


import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PowerLineService {

    @Autowired
    private PowerLineRepository powerLineRepository;

    public List<PowerLine> findAll() {

        return powerLineRepository.findAll();
    }
    public Page<PowerLine> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("namePowerLine"));
        return powerLineRepository.findAll(pageRequest);
    }


    public PowerLine findOne(long id) {
        return powerLineRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(PowerLine powerLine) {
        List<PowerLine> powerLineList = powerLine.getSubstation().getPowerLineList();
        if (powerLineList != null) {
            for (PowerLine line : powerLineList) {
                if (line.getNamePowerLine().equals(powerLine.getNamePowerLine())) {
                    throw new DublicateNameException("Такое имя уже существует");
                }
            }
        }
        powerLineRepository.save(powerLine);
    }

    @Transactional
    public void update(long id, PowerLine updatedPowerLine) {
        PowerLine powerLineToBeUpdated = powerLineRepository.findById(id).get();
        updatedPowerLine.setId(id);

        powerLineRepository.save(updatedPowerLine);
    }

    @Transactional
    public void delete(long id) {
        powerLineRepository.deleteById(id);
    }
}
