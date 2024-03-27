package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatCapitulosCie10;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatCapitulosCie10Repository extends JpaRepository<CatCapitulosCie10, Integer>, JpaSpecificationExecutor {
}
