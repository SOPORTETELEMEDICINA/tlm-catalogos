package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatImc519PSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatImc519PSPRepository extends JpaRepository<CatImc519PSP, Integer>, JpaSpecificationExecutor {
}

