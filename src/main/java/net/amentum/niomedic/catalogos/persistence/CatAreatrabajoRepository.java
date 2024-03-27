package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatAreatrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface CatAreatrabajoRepository extends JpaRepository<CatAreatrabajo, Integer>, JpaSpecificationExecutor {
}
