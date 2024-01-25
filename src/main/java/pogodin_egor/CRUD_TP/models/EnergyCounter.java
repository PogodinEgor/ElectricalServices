package pogodin_egor.CRUD_TP.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "energy_counter")
@Data
@NoArgsConstructor
public class EnergyCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counter_type", referencedColumnName = "id")
    private TypeCounter counterType;

    @Column(name = "manufacturingYear")
    @NotNull(message = "Год выпуска не может быть пустым.")
    private Integer manufacturingYear;

    @NotEmpty(message = "Номер п/у не может быть пустым.")
    @Column(name = "serialNumber", unique = true)
    private String serialNumber;

    @Column(name = "amperage")
    private String amperage;

    @Column(name = "voltage")
    private String voltage;

    @Column(name = "gear_ratio")
    private Integer gearRatio; //передаточное число счетчика

    @Column(name = "accuracy_class")
    private Double accuracyClass;// класс точности

    @Column(name = "bit_depth")
    private Double bitDepth; // разрядность

    @Column(name = "quarter_verification")
    private String quarterVerification;

    @Column(name = "calibration_interval")
    private Integer calibrationInterval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_line_id", referencedColumnName = "id")
    private PowerLine line;

    @OneToMany(mappedBy = "counter", cascade = CascadeType.MERGE)
    private List <EnergyMeasurement> energyMeasurements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_consumer_id", referencedColumnName = "id")
    private EnergyConsumer energyConsumer;

    @Column(name = "address")
    private String address;








}
