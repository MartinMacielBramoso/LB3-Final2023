package practica.examen.practicalabo3nro1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "costumer")
    private String costumer;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarEntity car;

    @Column(name = "rented_days")
    private Integer rentedDays;

    @Column(name = "start_rent")
    private LocalDateTime startRent;

    @Column(name = "end_rent")
    private LocalDateTime endRent;

    @Column(name = "total_price")
    private Double totalPrice;
}
