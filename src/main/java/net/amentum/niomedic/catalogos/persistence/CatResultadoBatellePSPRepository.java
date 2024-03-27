package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatResultadoBatellePSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatResultadoBatellePSPRepository extends JpaRepository<CatResultadoBatellePSP, Integer>, JpaSpecificationExecutor {
}

