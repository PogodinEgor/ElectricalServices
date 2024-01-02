package pogodin_egor.CRUD_TP.validator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pogodin_egor.CRUD_TP.models.TransformatorSubstation;
import pogodin_egor.CRUD_TP.services.TransformatorSubstationService;

import java.util.List;

@Component
@AllArgsConstructor
public class TransformatorSubstationValidator implements Validator {
    @Autowired
    private TransformatorSubstationService transformatorSubstationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return TransformatorSubstation.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        TransformatorSubstation transformatorSubstation = (TransformatorSubstation) target;
        List<TransformatorSubstation> transformatorSubstationList = transformatorSubstation.getPowerStation().getTransformatorSubstationList();

        for (TransformatorSubstation substation : transformatorSubstationList) {
            if(substation.getNameTransformatorSubstation().equals(transformatorSubstation.getNameTransformatorSubstation())){
                errors.rejectValue("nameTransformatorSubstation", "", "Такое название существует.");
            }
        }

    }
}
