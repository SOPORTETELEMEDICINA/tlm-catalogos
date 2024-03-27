package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatMunicipiosDom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatMunicipiosDomRepository extends JpaRepository<CatMunicipiosDom, Integer>, JpaSpecificationExecutor {
}
