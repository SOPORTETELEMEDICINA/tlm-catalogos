package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatRiesgosDetectadosPSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatRiesgosDetectadosPSPRepository extends JpaRepository<CatRiesgosDetectadosPSP, Integer>, JpaSpecificationExecutor {
}

