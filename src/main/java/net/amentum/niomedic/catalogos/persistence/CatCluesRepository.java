package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatClues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository

public interface CatCluesRepository extends JpaRepository<CatClues, Integer>, JpaSpecificationExecutor{
}
