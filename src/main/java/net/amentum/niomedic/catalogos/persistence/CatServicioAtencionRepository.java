package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatServicioAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatServicioAtencionRepository extends JpaRepository<CatServicioAtencion, Integer>, JpaSpecificationExecutor {
}

