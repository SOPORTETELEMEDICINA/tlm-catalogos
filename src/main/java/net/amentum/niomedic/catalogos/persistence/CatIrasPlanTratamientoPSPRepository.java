package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatIrasPlanTratamientoPSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatIrasPlanTratamientoPSPRepository extends JpaRepository<CatIrasPlanTratamientoPSP, Integer>, JpaSpecificationExecutor {
}

