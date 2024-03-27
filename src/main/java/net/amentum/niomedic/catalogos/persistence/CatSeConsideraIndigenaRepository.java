package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatSeConsideraIndigena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CatSeConsideraIndigenaRepository extends JpaRepository<CatSeConsideraIndigena, Integer>, JpaSpecificationExecutor {
}

