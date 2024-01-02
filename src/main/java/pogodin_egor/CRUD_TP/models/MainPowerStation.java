package pogodin_egor.CRUD_TP.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="main_power_station")
@Data
@NoArgsConstructor
public class MainPowerStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_power_station")
    private String namePowerStation;

    @OneToMany(mappedBy = "powerStation", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List <TransformatorSubstation> transformatorSubstationList = new ArrayList<>();

}
