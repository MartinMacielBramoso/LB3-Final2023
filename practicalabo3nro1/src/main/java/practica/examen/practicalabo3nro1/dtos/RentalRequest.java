package practica.examen.practicalabo3nro1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequest {

    private String costumer;
    private Long carId;
    private LocalDateTime startRent;
    private LocalDateTime endRent;
    private Double totalPrice;
}
