package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatEntidadesDom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatEntidadesDomRepository extends JpaRepository<CatEntidadesDom, Integer>, JpaSpecificationExecutor {
}
