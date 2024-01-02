package pogodin_egor.CRUD_TP.validator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pogodin_egor.CRUD_TP.models.EnergyConsumer;
import pogodin_egor.CRUD_TP.services.EnergyConsumerService;

@Component
@AllArgsConstructor
public class EnergyConsumerValidator implements Validator {

    @Autowired
    private final EnergyConsumerService energyConsumerService;

    @Override
    public boolean supports(Class<?> aClass) {
        return EnergyConsumer.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        EnergyConsumer energyConsumer = (EnergyConsumer) target;

        if(energyConsumer.getFirstName().isEmpty()){
            errors.rejectValue("energyConsumer", "", "Имя не может быть пустым.");
        }
        if(energyConsumer.getLastName().isEmpty()){
            errors.rejectValue("energyConsumer", "", "Фамилия не может быть пустой.");
        }

    }
}
