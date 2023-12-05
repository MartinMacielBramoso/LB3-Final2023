package practica.examen.practicalabo3nro1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponse {

    private Long id;
    private String costumer;
    private CarRequest car;
    private Integer rentedDays;
    private LocalDateTime startRent;
    private LocalDateTime endRent;
    private Double totalPrice;
}
