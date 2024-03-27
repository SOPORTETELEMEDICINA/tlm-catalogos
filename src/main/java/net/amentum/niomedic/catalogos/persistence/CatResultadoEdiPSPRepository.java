package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatResultadoEdiPSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatResultadoEdiPSPRepository extends JpaRepository<CatResultadoEdiPSP, Integer>, JpaSpecificationExecutor {
}

