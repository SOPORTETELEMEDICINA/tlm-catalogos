package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatOrigenDificultad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatOrigenDificultadRepository extends JpaRepository<CatOrigenDificultad, Integer>, JpaSpecificationExecutor {
}

