package pogodin_egor.CRUD_TP.validator;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pogodin_egor.CRUD_TP.models.EnergyCounter;
import pogodin_egor.CRUD_TP.models.TypeCounter;
import pogodin_egor.CRUD_TP.services.EnergyCounterService;
import pogodin_egor.CRUD_TP.services.TypeCounterService;

import java.util.List;

@Component
@AllArgsConstructor
public class EnergyCounterValidator implements Validator {

    @Autowired
    private EnergyCounterService energyCounterService;

    @Autowired
    private TypeCounterService typeCounterService;

    @Override
    public boolean supports(Class<?> aClass) {
        return EnergyCounter.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        EnergyCounter energyCounter = (EnergyCounter) target;

        List<EnergyCounter> energyCounterList = typeCounterService.findOne(energyCounter.getCounterType().getId()).getCounterList();
        for (EnergyCounter counter : energyCounterList) {
            if(counter.getSerialNumber().equals(energyCounter.getSerialNumber()) && energyCounter.getId()== null){
                errors.rejectValue("serialNumber", "", "Такой номер пу уже существует.");
            }
        }

        if(energyCounter.getCounterType() == null){
            errors.rejectValue("counterType", "", "Тип счетчика не может быть пустым.");
        }
        if(energyCounter.getManufacturingYear() == null){
            errors.rejectValue("manufacturingYear", "", "Год выпуска счетчика должен быть указан.");
        }
    }
}



