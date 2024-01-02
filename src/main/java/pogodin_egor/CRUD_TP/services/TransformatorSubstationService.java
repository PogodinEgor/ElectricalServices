package pogodin_egor.CRUD_TP.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pogodin_egor.CRUD_TP.exception.DublicateNameException;
import pogodin_egor.CRUD_TP.models.TransformatorSubstation;
import pogodin_egor.CRUD_TP.repositories.TransformatorSubstationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class TransformatorSubstationService {

    @Autowired
    private TransformatorSubstationRepository transformatorSubstationRepository;

    public Page<TransformatorSubstation> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("powerStation.namePowerStation"));
        return transformatorSubstationRepository.findAll(pageRequest);
    }

    public List<TransformatorSubstation> findAll(){
        return transformatorSubstationRepository.findAll();
    }

    public List<TransformatorSubstation> findByNameTransformatorSubstation(String nameTransformatorSubstation){
        return transformatorSubstationRepository.findAllByNameTransformatorSubstation(nameTransformatorSubstation);
    }

    public TransformatorSubstation findOne(long id){
        return transformatorSubstationRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(TransformatorSubstation transformatorSubstation) throws DublicateNameException {
        List<TransformatorSubstation> transformatorSubstationList = transformatorSubstation.getPowerStation().getTransformatorSubstationList();
        for (TransformatorSubstation substation : transformatorSubstationList) {
            if(substation.getNameTransformatorSubstation().equalsIgnoreCase(transformatorSubstation.getNameTransformatorSubstation())){
                throw new DublicateNameException("Такое имя уже существует");
            }
        }

        transformatorSubstationRepository.save(transformatorSubstation);
    }

    @Transactional
    public void update(long id, TransformatorSubstation updatedTransformatorSubstation) {
        TransformatorSubstation transformatorSubstationToBeUpdated = transformatorSubstationRepository.findById(id).get();
        updatedTransformatorSubstation.setId(id);

        transformatorSubstationRepository.save(updatedTransformatorSubstation);
    }

    @Transactional
    public void delete(long id) {
        transformatorSubstationRepository.deleteById(id);
    }
}
