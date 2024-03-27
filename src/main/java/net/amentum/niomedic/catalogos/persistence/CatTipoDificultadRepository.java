package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatTipoDificultad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatTipoDificultadRepository extends JpaRepository<CatTipoDificultad, Integer>, JpaSpecificationExecutor {
}

