package practica.examen.practicalabo3nro1.services;

import org.springframework.stereotype.Service;
import practica.examen.practicalabo3nro1.dtos.CarRequest;
import practica.examen.practicalabo3nro1.dtos.CarResponse;
import practica.examen.practicalabo3nro1.dtos.CarTypeRequest;
import practica.examen.practicalabo3nro1.dtos.CarTypeResponse;

import java.util.List;

@Service
public interface CarService {
    CarTypeResponse createCarType(CarTypeRequest carTypeRequest);
    List<CarTypeResponse> getCarTypes();
    CarResponse createCar(CarRequest carRequest);
    List<CarResponse> getCars();
    CarResponse getCarById(Long carId);
    CarResponse editCar(Long carId, CarRequest carRequest);
    CarResponse removeCar(Long carId);
    CarResponse findByBrandAndModel(String brand, String model);
}
