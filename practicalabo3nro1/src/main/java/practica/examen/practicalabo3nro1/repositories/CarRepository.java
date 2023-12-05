package practica.examen.practicalabo3nro1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.examen.practicalabo3nro1.entities.CarEntity;
@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    CarEntity findByBrandAndModel(String brand, String model);
}
