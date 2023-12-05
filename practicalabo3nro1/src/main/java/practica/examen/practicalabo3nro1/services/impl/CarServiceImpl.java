package practica.examen.practicalabo3nro1.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import practica.examen.practicalabo3nro1.dtos.CarRequest;
import practica.examen.practicalabo3nro1.dtos.CarResponse;
import practica.examen.practicalabo3nro1.dtos.CarTypeRequest;
import practica.examen.practicalabo3nro1.dtos.CarTypeResponse;
import practica.examen.practicalabo3nro1.entities.CarEntity;
import practica.examen.practicalabo3nro1.entities.CarTypeEntity;
import practica.examen.practicalabo3nro1.repositories.CarRepository;
import practica.examen.practicalabo3nro1.repositories.CarTypeRepository;
import practica.examen.practicalabo3nro1.services.CarService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarTypeRepository carTypeRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, CarTypeRepository carTypeRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.carTypeRepository = carTypeRepository;
        this.modelMapper = modelMapper;
    }

    /* ---> CarType Methods <--- */
    @Override
    public CarTypeResponse createCarType(CarTypeRequest carTypeRequest){

        CarTypeEntity existingCarType = carTypeRepository.findByType(carTypeRequest.getType());
        if (existingCarType != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de automóvil ya existe.");
        }

        CarTypeEntity newCarType = new CarTypeEntity();
        newCarType.setType(carTypeRequest.getType());
        newCarType.setPrice(carTypeRequest.getPrice());

        carTypeRepository.save(newCarType);

        return modelMapper.map(newCarType, CarTypeResponse.class);
    }
    @Override
    public List<CarTypeResponse> getCarTypes(){
        List<CarTypeEntity> carTypes = carTypeRepository.findAll();
        return carTypes.stream()
                .map(carType -> modelMapper.map(carType, CarTypeResponse.class))
                .collect(Collectors.toList());
    }

    /* ---> Car Methods <--- */
    @Override
    public CarResponse createCar(CarRequest carRequest) {
        CarEntity existingCar = carRepository.findByBrandAndModel(carRequest.getBrand(),carRequest.getModel());
        if (existingCar != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El automóvil con esas caracterisiticas ya existe.");
        }

        CarEntity newCar = new CarEntity();
        CarTypeEntity carType = carTypeRepository.findById(carRequest.getCarTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Car type not found"));

        newCar.setCarType(carType);
        newCar.setBrand(carRequest.getBrand());
        newCar.setModel(carRequest.getModel());
        carRepository.save(newCar);
        return modelMapper.map(newCar, CarResponse.class);
    }
    @Override
    public List<CarResponse> getCars(){
        List<CarEntity> cars = carRepository.findAll();
        return cars.stream()
                .map(car -> modelMapper.map(car, CarResponse.class))
                .collect(Collectors.toList());
    }
    @Override
    public CarResponse getCarById(Long carId){
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car with id: " + carId + "not found"));

        return modelMapper.map(car, CarResponse.class);
    }
    @Override
    public CarResponse editCar(Long carId, CarRequest carRequest){
        CarEntity existingCar = carRepository.findByBrandAndModel(carRequest.getBrand(), carRequest.getModel());

        // Verificar si el automóvil existe y si es diferente del automóvil que se está editando
        if (existingCar != null && !existingCar.getId().equals(carId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El automóvil con esas características ya existe.");
        }

        CarEntity newCar = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car with id: " + carId + " not found"));

        CarTypeEntity carType = carTypeRepository.findById(carRequest.getCarTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Car type not found"));

        if (!newCar.getBrand().equals(carRequest.getBrand()) || !newCar.getModel().equals(carRequest.getModel())) {
            newCar.setBrand(carRequest.getBrand());
            newCar.setModel(carRequest.getModel());
        }

        if (!newCar.getCarType().getId().equals(carRequest.getCarTypeId())) {
            newCar.setCarType(carType);
        }

        carRepository.save(newCar);

        return modelMapper.map(newCar, CarResponse.class);
    }

    @Override
    public CarResponse removeCar(Long carId){
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car with id: " + carId + "not found"));

        CarResponse carResponse = modelMapper.map(car, CarResponse.class);

        carRepository.delete(car);
        return carResponse;
    }
    @Override
    public CarResponse findByBrandAndModel(String brand, String model){
        CarEntity car = carRepository.findByBrandAndModel(brand,model);

        if (car == null){
            throw new EntityNotFoundException("Car with brand: " + brand + " and model: " + model +" was not found");
        }

        return modelMapper.map(car, CarResponse.class);
    }

}
