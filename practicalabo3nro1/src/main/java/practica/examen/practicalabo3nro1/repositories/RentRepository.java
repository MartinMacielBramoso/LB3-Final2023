package practica.examen.practicalabo3nro1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.examen.practicalabo3nro1.entities.RentalEntity;
@Repository
public interface RentRepository extends JpaRepository<RentalEntity, Long> {
}
