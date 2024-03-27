package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatSeAutodenominaAfromexicano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatSeAutodenominaAfromexicanoRepository extends JpaRepository<CatSeAutodenominaAfromexicano, Integer>, JpaSpecificationExecutor {
}

