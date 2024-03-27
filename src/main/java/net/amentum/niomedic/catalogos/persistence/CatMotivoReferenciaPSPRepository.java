package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatMotivoReferenciaPSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatMotivoReferenciaPSPRepository extends JpaRepository<CatMotivoReferenciaPSP, Integer>, JpaSpecificationExecutor {
}

