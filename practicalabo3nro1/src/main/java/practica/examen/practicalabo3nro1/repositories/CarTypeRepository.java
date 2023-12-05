package practica.examen.practicalabo3nro1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.examen.practicalabo3nro1.entities.CarTypeEntity;

import java.util.Optional;

@Repository
public interface CarTypeRepository extends JpaRepository<CarTypeEntity, Long> {
    CarTypeEntity findByType(String type);
}
