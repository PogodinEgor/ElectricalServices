package pogodin_egor.CRUD_TP.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "energy_consumer")
@Data
@NoArgsConstructor
public class EnergyConsumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "Имя не должно быть пустым")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Имя не должно быть пустым")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "energyConsumer", cascade = CascadeType.REMOVE)
    private List <EnergyCounter> counterList;


}
