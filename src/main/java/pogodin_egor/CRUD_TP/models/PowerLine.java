package pogodin_egor.CRUD_TP.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "power_line")
@Data
@NoArgsConstructor
public class PowerLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_power_line")
    private String namePowerLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transformator_substation_id", referencedColumnName = "id")
    private TransformatorSubstation substation;

    @OneToMany(mappedBy = "line", cascade = CascadeType.MERGE)
    private List <EnergyCounter> energyCounterList;
}
