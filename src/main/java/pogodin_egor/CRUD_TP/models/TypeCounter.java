package pogodin_egor.CRUD_TP.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "type_counter")
@Data
@NoArgsConstructor
public class TypeCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Тип счетчика должен быть назначен.")
    @Column(name = "type_name")
    private String typeName;

    @OneToMany(mappedBy = "counterType", cascade = CascadeType.MERGE)
    List<EnergyCounter> counterList;

}
