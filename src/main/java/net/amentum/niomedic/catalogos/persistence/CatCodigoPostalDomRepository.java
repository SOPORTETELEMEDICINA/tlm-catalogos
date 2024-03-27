package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatCodigoPostalDom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatCodigoPostalDomRepository extends JpaRepository<CatCodigoPostalDom, Long>, JpaSpecificationExecutor {
}
