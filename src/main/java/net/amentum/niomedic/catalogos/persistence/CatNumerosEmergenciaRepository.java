package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatNumerosEmergencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatNumerosEmergenciaRepository extends JpaRepository<CatNumerosEmergencia, Integer>, JpaSpecificationExecutor {
}
