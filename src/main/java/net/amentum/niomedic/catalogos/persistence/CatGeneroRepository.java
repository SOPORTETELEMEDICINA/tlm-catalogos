package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatGenero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatGeneroRepository extends JpaRepository<CatGenero, Integer>, JpaSpecificationExecutor {
}

