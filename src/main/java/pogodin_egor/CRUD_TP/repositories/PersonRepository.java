package pogodin_egor.CRUD_TP.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pogodin_egor.CRUD_TP.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByLogin(String login);
}
