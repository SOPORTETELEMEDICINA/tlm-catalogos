package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatEdasPlanTratamientoPSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatEdasPlanTratamientoPSPRepository extends JpaRepository<CatEdasPlanTratamientoPSP, Integer>, JpaSpecificationExecutor {
}

