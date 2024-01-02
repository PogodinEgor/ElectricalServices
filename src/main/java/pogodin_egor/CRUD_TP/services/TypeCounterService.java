package pogodin_egor.CRUD_TP.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pogodin_egor.CRUD_TP.models.EnergyCounter;
import pogodin_egor.CRUD_TP.models.TypeCounter;
import pogodin_egor.CRUD_TP.repositories.TypeCounterRepository;

import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class TypeCounterService {

    @Autowired
    private TypeCounterRepository typeCounterRepository;

    public List<TypeCounter> findAll(){return typeCounterRepository.findAll();}

    public Page<TypeCounter> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("typeName"));
        return typeCounterRepository.findAll(pageRequest);
    }

    public TypeCounter findOne(long id){
        return typeCounterRepository.findById(id).orElse(null);
    }

    public Optional<TypeCounter> findByNameTypeName(String typeName){
        return   typeCounterRepository.findByTypeName(typeName);
    }

    @Transactional
    public void save(TypeCounter typeCounter) {
        typeCounterRepository.save(typeCounter);
    }

    @Transactional
    public void update(long id, TypeCounter updatedTypeCounter) {
        TypeCounter typeCounterToBeUpdated = typeCounterRepository.findById(id).get();
        updatedTypeCounter.setId(id);

        typeCounterRepository.save(updatedTypeCounter);
    }

    @Transactional
    public void delete(long id) {
        typeCounterRepository.deleteById(id);
    }
}


