package pogodin_egor.CRUD_TP.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pogodin_egor.CRUD_TP.models.MainPowerStation;
import pogodin_egor.CRUD_TP.services.MainPowerStationService;

@Component
@AllArgsConstructor
public class MainPowerStationValidator implements Validator {

    @Autowired
    private MainPowerStationService mainPowerStationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return MainPowerStation.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MainPowerStation mainPowerStation = (MainPowerStation) target;

        if (mainPowerStationService.findByNamePowerStation(mainPowerStation.getNamePowerStation()).isPresent()) {
            errors.rejectValue("namePowerStation", "", "Такое название существует.");
        }
    }
}
