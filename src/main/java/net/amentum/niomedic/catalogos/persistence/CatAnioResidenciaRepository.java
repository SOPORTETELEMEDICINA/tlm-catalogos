package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatAnioResidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatAnioResidenciaRepository extends JpaRepository<CatAnioResidencia, Integer>, JpaSpecificationExecutor {
}

