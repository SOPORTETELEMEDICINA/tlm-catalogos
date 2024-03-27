package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatPrograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatProgramaRepository extends JpaRepository<CatPrograma, Integer>, JpaSpecificationExecutor {
}

