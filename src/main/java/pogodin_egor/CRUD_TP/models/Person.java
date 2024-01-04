package pogodin_egor.CRUD_TP.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pogodin_egor.CRUD_TP.roles.Role;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
public class Person  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public Person(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

}
