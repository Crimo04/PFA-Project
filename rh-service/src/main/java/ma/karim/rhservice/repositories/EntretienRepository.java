package ma.karim.rhservice.repositories;


import ma.karim.rhservice.entities.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntretienRepository extends JpaRepository<Entretien,Long> {

      public List<Entretien> findEntretienByTechLeadId(Long id);
}
