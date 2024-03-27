package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatMedicamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatMedicamentosRepository extends JpaRepository<CatMedicamentos, Integer>, JpaSpecificationExecutor {
}
