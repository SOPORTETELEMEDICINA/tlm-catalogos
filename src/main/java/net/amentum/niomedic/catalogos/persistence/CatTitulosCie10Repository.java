package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatTitulosCie10;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatTitulosCie10Repository extends JpaRepository<CatTitulosCie10, Integer>, JpaSpecificationExecutor {
}
