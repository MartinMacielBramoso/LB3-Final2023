package practica.examen.practicalabo3nro1.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import practica.examen.practicalabo3nro1.dtos.CarRequest;
import practica.examen.practicalabo3nro1.dtos.CarResponse;
import practica.examen.practicalabo3nro1.dtos.CarTypeRequest;
import practica.examen.practicalabo3nro1.dtos.CarTypeResponse;
import practica.examen.practicalabo3nro1.entities.CarEntity;
import practica.examen.practicalabo3nro1.entities.CarTypeEntity;
import practica.examen.practicalabo3nro1.repositories.CarRepository;
import practica.examen.practicalabo3nro1.repositories.CarTypeRepository;
import practica.examen.practicalabo3nro1.services.impl.CarServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarTypeRepository carTypeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CarServiceImpl carService;


    @Test
    public void testCreateCarType_WhenCarTypeDoesNotExist_ShouldCreateCarType() {
        CarTypeRequest request = new CarTypeRequest();
        request.setType("SUV");
        request.setPrice(50000.0);

        when(carTypeRepository.findByType("SUV")).thenReturn(null);
        when(modelMapper.map(any(), eq(CarTypeResponse.class))).thenReturn(new CarTypeResponse());

        CarTypeResponse response = carService.createCarType(request);

        verify(carTypeRepository, times(1)).findByType("SUV");
        verify(carTypeRepository, times(1)).save(any());
        verify(modelMapper, times(1)).map(any(), eq(CarTypeResponse.class));

        // Asegurar que la respuesta no es nula
        assertEquals(response, new CarTypeResponse());
    }

    @Test
    public void testGetCarTypes_ShouldReturnListOfCarTypeResponses() {
        CarTypeEntity carTypeEntity = new CarTypeEntity();
        carTypeEntity.setId(1L);
        carTypeEntity.setType("SUV");
        carTypeEntity.setPrice(50000.0);

        List<CarTypeEntity> carTypeEntities = Collections.singletonList(carTypeEntity);

        when(carTypeRepository.findAll()).thenReturn(carTypeEntities);
        when(modelMapper.map(any(), eq(CarTypeResponse.class))).thenReturn(new CarTypeResponse());

        List<CarTypeResponse> responses = carService.getCarTypes();

        verify(carTypeRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(any(), eq(CarTypeResponse.class));

        // Asegurar que la lista no está vacía
        assertEquals(responses.size(), 1);
    }

    @Test
    public void testCreateCar_WhenCarDoesNotExist_ShouldCreateCar() {
        CarRequest carRequest = new CarRequest();
        carRequest.setCarTypeId(1L);
        carRequest.setBrand("Toyota");
        carRequest.setModel("Corolla");

        // Simular el CarTypeEntity que se espera devolver al buscar por ID
        CarTypeEntity carType = new CarTypeEntity();
        carType.setId(1L);
        carType.setType("Type");
        carType.setPrice(50000.0);

        // Configurar el comportamiento del carTypeRepository para devolver un Optional con el CarTypeEntity
        when(carTypeRepository.findById(1L)).thenReturn(Optional.of(carType));

        CarEntity existingCar = null;
        when(carRepository.findByBrandAndModel("Toyota", "Corolla")).thenReturn(null);

        CarEntity newCar = new CarEntity();
        newCar.setId(1L);
        newCar.setBrand("Toyota");
        newCar.setModel("Corolla");
        when(carRepository.save(any())).thenReturn(newCar);

        CarResponse mappedResponse = new CarResponse();
        when(modelMapper.map(any(), eq(CarResponse.class))).thenReturn(mappedResponse);

        CarResponse response = carService.createCar(carRequest);

        verify(carRepository, times(1)).findByBrandAndModel("Toyota", "Corolla");
        verify(carRepository, times(1)).save(any());
        verify(modelMapper, times(1)).map(any(), eq(CarResponse.class));

        assertNotNull(response);
        assertEquals(mappedResponse, response);
    }

    @Test
    public void testGetCars_ShouldReturnListOfCarResponses() {
        CarEntity carEntity = new CarEntity();
        carEntity.setId(1L);
        carEntity.setBrand("Toyota");
        carEntity.setModel("Corolla");

        List<CarEntity> carEntities = Collections.singletonList(carEntity);

        when(carRepository.findAll()).thenReturn(carEntities);
        when(modelMapper.map(any(), eq(CarResponse.class))).thenReturn(new CarResponse());

        List<CarResponse> responses = carService.getCars();

        verify(carRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(any(), eq(CarResponse.class));

        assertEquals(responses.size(), 1);
    }

    @Test
    public void testGetCarById_ShouldReturnCarResponse() {
        Long carId = 1L;
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carId);
        carEntity.setBrand("Toyota");
        carEntity.setModel("Corolla");

        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));
        when(modelMapper.map(any(), eq(CarResponse.class))).thenReturn(new CarResponse());

        CarResponse response = carService.getCarById(carId);

        verify(carRepository, times(1)).findById(carId);
        verify(modelMapper, times(1)).map(any(), eq(CarResponse.class));

        assertNotNull(response);
    }

    @Test
    public void testEditCar_WhenCarExists_ShouldEditCar() {
        Long carId = 1L;
        CarRequest carRequest = new CarRequest();
        carRequest.setCarTypeId(1L);
        carRequest.setBrand("Toyota");
        carRequest.setModel("Corolla");

        CarTypeEntity carTypeEntity = new CarTypeEntity();
        carTypeEntity.setId(1L);

        // Simular el CarTypeEntity al buscar por ID en el carTypeRepository
        when(carTypeRepository.findById(1L)).thenReturn(Optional.of(carTypeEntity));

        CarEntity existingCar = new CarEntity();
        existingCar.setId(carId);
        existingCar.setBrand("Toyota");
        existingCar.setModel("Corolla");
        when(carRepository.findByBrandAndModel("Toyota", "Corolla")).thenReturn(existingCar);

        CarEntity newCar = new CarEntity();
        newCar.setId(carId);
        newCar.setBrand("Toyota");
        newCar.setModel("Corolla");
        newCar.setCarType(carTypeEntity); // Asignar el CarTypeEntity

        when(carRepository.findById(carId)).thenReturn(Optional.of(newCar));

        when(modelMapper.map(any(), eq(CarResponse.class))).thenReturn(new CarResponse());

        CarResponse response = carService.editCar(carId, carRequest);

        verify(carTypeRepository, times(1)).findById(1L); // Verificar la búsqueda del CarTypeEntity
        verify(carRepository, times(1)).findByBrandAndModel("Toyota", "Corolla");
        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, times(1)).save(any()); // Verificar el guardado del automóvil

        assertNotNull(response);
    }


    @Test
    public void testRemoveCar_ShouldReturnCarResponse() {
        Long carId = 1L;
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carId);
        carEntity.setBrand("Toyota");
        carEntity.setModel("Corolla");

        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));
        when(modelMapper.map(any(), eq(CarResponse.class))).thenReturn(new CarResponse());

        CarResponse response = carService.removeCar(carId);

        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, times(1)).delete(carEntity);
        verify(modelMapper, times(1)).map(any(), eq(CarResponse.class));

        assertNotNull(response);
    }

    @Test
    public void testFindByBrandAndModel_WhenCarExists_ShouldReturnCarResponse() {
        String brand = "Toyota";
        String model = "Corolla";

        CarEntity carEntity = new CarEntity();
        carEntity.setId(1L);
        carEntity.setBrand(brand);
        carEntity.setModel(model);

        when(carRepository.findByBrandAndModel(brand, model)).thenReturn(carEntity);
        when(modelMapper.map(any(), eq(CarResponse.class))).thenReturn(new CarResponse());

        CarResponse response = carService.findByBrandAndModel(brand, model);

        verify(carRepository, times(1)).findByBrandAndModel(brand, model);
        verify(modelMapper, times(1)).map(any(), eq(CarResponse.class));

        assertNotNull(response);
    }


}
