package pogodin_egor.CRUD_TP.validator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pogodin_egor.CRUD_TP.exception.DublicateNameException;
import pogodin_egor.CRUD_TP.models.Person;
import pogodin_egor.CRUD_TP.services.PersonService;
;


@Component
@AllArgsConstructor
public class PersonValidator implements Validator {
    @Autowired
    private PersonService personService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;

        try {
            personService.findByLogin(person.getLogin());
        } catch (DublicateNameException ignored) {
            return; // все ок, пользователь не найден
        }

        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}

