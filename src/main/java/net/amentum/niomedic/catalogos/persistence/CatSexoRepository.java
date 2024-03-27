package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatSexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatSexoRepository extends JpaRepository<CatSexo, Integer>, JpaSpecificationExecutor {
}

