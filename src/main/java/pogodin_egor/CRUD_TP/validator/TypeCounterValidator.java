package pogodin_egor.CRUD_TP.validator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pogodin_egor.CRUD_TP.models.TypeCounter;
import pogodin_egor.CRUD_TP.services.TypeCounterService;


@Component
@AllArgsConstructor
public class TypeCounterValidator implements Validator {

    @Autowired
    private TypeCounterService typeCounterService;
    @Override
    public boolean supports(Class<?> aClass) {
        return TypeCounter.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        TypeCounter typeCounter = (TypeCounter) target;

        if(typeCounter.getTypeName().isEmpty()){
            errors.rejectValue("typeName", "", "Имя не может быть пустым.");
        }
    }
}
