package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatSubtitulosCie10;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatSubtitulosCie10Repository extends JpaRepository<CatSubtitulosCie10, Integer>, JpaSpecificationExecutor {
}
