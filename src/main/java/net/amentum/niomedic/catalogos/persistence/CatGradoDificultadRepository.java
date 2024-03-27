package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatGradoDificultad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatGradoDificultadRepository extends JpaRepository<CatGradoDificultad, Integer>, JpaSpecificationExecutor {
}

