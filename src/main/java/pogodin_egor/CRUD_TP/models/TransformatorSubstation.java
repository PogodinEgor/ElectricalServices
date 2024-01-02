package pogodin_egor.CRUD_TP.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "transformator_substation")
@Data
@NoArgsConstructor
public class TransformatorSubstation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_transformator_substation")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String nameTransformatorSubstation;

    @ManyToOne(cascade = CascadeType.MERGE,optional = true)
    @JoinColumn(name = "main_power_station_id",referencedColumnName = "id")
    private MainPowerStation powerStation;

    @OneToMany(mappedBy = "substation", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<PowerLine> powerLineList;
}
