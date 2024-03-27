package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatTipoAsentamientoDom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatTipoAsentamientoDomRepository extends JpaRepository<CatTipoAsentamientoDom, Integer>, JpaSpecificationExecutor {
}
