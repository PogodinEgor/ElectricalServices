package pogodin_egor.CRUD_TP.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pogodin_egor.CRUD_TP.models.Person;
import pogodin_egor.CRUD_TP.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Person> findAll(){return personRepository.findAll();}

    public Optional<Person> findByLogin(String login){
        return personRepository.findByLogin(login);
    }
    public Person findOne(long id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        String encodedPassword = passwordEncoder.encode(person.getPassword());

        person.setPassword(encodedPassword);

        personRepository.save(person);
    }

    @Transactional
    public void update(long id, Person person) {
        Optional<Person> personToBeUpdated = personRepository.findById(id);
        if (personToBeUpdated.isPresent()) {
            Person existingPerson = personToBeUpdated.get();

            existingPerson.setLogin(person.getLogin());
            existingPerson.setRole(person.getRole());

            if (!person.getPassword().isEmpty()) {
                existingPerson.setPassword(passwordEncoder.encode(person.getPassword()));
            }

            personRepository.save(existingPerson);
        }
    }

    @Transactional
    public void delete(long id) {
        personRepository.deleteById(id);
    }

}
