package pogodin_egor.CRUD_TP.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "energy_measurement")
@Data
public class EnergyMeasurement {

    public EnergyMeasurement() {
        this.date = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "meter_readings")
    private Double meterReadings;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = CascadeType.MERGE,optional = true)
    @JoinColumn(name = "energy_counter_id", referencedColumnName = "id")
    private EnergyCounter counter;
}
