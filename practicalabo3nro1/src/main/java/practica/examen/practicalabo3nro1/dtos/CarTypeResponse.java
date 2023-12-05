package practica.examen.practicalabo3nro1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarTypeResponse {
    private Long id;
    private String type;
    private Double price;
}
