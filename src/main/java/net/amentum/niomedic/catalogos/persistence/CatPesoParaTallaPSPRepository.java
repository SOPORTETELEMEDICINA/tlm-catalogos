package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatPesoParaTallaPSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatPesoParaTallaPSPRepository extends JpaRepository<CatPesoParaTallaPSP, Integer>, JpaSpecificationExecutor {
}

