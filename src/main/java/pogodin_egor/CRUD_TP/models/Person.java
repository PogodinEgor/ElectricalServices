package pogodin_egor.CRUD_TP.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "password")
    private String password;

    public Person(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
