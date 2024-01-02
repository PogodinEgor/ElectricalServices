package pogodin_egor.CRUD_TP.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import pogodin_egor.CRUD_TP.models.EnergyConsumer;
import pogodin_egor.CRUD_TP.repositories.EnergyConsumerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EnergyConsumerService {

    @Autowired
    private EnergyConsumerRepository energyConsumerRepository;

    public List<EnergyConsumer> findAll() {return energyConsumerRepository.findAll();}

    public Page<EnergyConsumer> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("lastName"));
        return energyConsumerRepository.findAll(pageRequest);
    }

    public EnergyConsumer findOne(long id) {
        return energyConsumerRepository.findById(id).orElse(null);
    }

    public Page<EnergyConsumer> findByLastNameIgnoreCase(String lastName, Pageable pageable) {
        return energyConsumerRepository.findByLastNameIgnoreCase(lastName, pageable);
    }


    @Transactional
    public void save(EnergyConsumer energyConsumer) {
        energyConsumerRepository.save(energyConsumer);
    }

    @Transactional
    public void update(long id, EnergyConsumer updatedConsumer) {
        Optional<EnergyConsumer> energyConsumerToBeUpdated = energyConsumerRepository.findById(id);
        if (energyConsumerToBeUpdated.isPresent()) {
            EnergyConsumer existingConsumer = energyConsumerToBeUpdated.get();

            existingConsumer.setFirstName(updatedConsumer.getFirstName());
            existingConsumer.setLastName(updatedConsumer.getLastName());
            existingConsumer.setMiddleName(updatedConsumer.getMiddleName());
            existingConsumer.setCounterList(updatedConsumer.getCounterList());

            energyConsumerRepository.save(existingConsumer);
        } else {
            throw new IllegalArgumentException("EnergyConsumer not found with id: " + id);
        }

    }


    @Transactional
    public void delete(long id) {
        energyConsumerRepository.deleteById(id);
    }
}
