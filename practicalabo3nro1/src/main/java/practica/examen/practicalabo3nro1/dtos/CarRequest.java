package practica.examen.practicalabo3nro1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    private Long carTypeId;
    private String brand;
    private String model;
}
