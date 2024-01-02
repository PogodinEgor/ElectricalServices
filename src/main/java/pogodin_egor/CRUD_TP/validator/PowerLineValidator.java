package pogodin_egor.CRUD_TP.validator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pogodin_egor.CRUD_TP.models.PowerLine;
import pogodin_egor.CRUD_TP.services.TransformatorSubstationService;


import java.util.List;

@Component
@AllArgsConstructor
public class PowerLineValidator implements Validator {
    @Autowired
    private TransformatorSubstationService transformatorSubstationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return PowerLine.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        PowerLine powerLine = (PowerLine) target;
        List<PowerLine> powerLineList = transformatorSubstationService.findOne(powerLine.getSubstation().getId()).getPowerLineList();


        for (PowerLine line : powerLineList) {
            if (line.getNamePowerLine().equals(powerLine.getNamePowerLine())) {
                errors.rejectValue("namePowerLine", "", "Такое название существует.");
            }
        }

    }
}
