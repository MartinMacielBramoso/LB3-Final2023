package practica.examen.practicalabo3nro1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_type_id")
    private CarTypeEntity carType;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;
}
