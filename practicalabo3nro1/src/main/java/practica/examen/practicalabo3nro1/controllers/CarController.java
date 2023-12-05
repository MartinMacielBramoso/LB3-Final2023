package practica.examen.practicalabo3nro1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import practica.examen.practicalabo3nro1.dtos.CarRequest;
import practica.examen.practicalabo3nro1.dtos.CarResponse;
import practica.examen.practicalabo3nro1.dtos.CarTypeRequest;
import practica.examen.practicalabo3nro1.dtos.CarTypeResponse;
import practica.examen.practicalabo3nro1.services.CarService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cars")
public class CarController {

    //TODO: ADD EXCEPTIONS/RESPONSE ENTITY
    //ADD FIND BY MODEL/BRAND

    @Autowired
    private CarService carService;

    @PostMapping("/car-types")
    public ResponseEntity<CarTypeResponse> createCarType(@RequestBody CarTypeRequest carTypeRequest){
        CarTypeResponse response = carService.createCarType(carTypeRequest);

        if (Objects.isNull(response)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos incompletos, por favor intentelo de nuevo.");
        }
        else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/car-types")
    public ResponseEntity<List<CarTypeResponse>> getCarTypes(){
        List<CarTypeResponse> response = carService.getCarTypes();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CarResponse> createCar(@RequestBody CarRequest carRequest){
        CarResponse response = carService.createCar(carRequest);

        if (Objects.isNull(response)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos incompletos, por favor intentelo de nuevo.");
        }
        else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<CarResponse>> getCars(){
        List<CarResponse> response = carService.getCars();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id){
        CarResponse response = carService.getCarById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> editCar(@PathVariable Long id, @RequestBody CarRequest carRequest){
        CarResponse response = carService.editCar(id,carRequest);

        if (Objects.isNull(response)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos incompletos, por favor intentelo de nuevo.");
        }
        else {
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarResponse> deleteCar(@PathVariable Long id){
        CarResponse response = carService.removeCar(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{brand}/{model}")
    public ResponseEntity<CarResponse> findCarByBrandAndModel(@PathVariable String brand, String model){
        CarResponse response = carService.findByBrandAndModel(brand,model);

        return ResponseEntity.ok(response);
    }
}
