package net.amentum.niomedic.catalogos.persistence;


import net.amentum.niomedic.catalogos.model.CatJornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface CatJornadaRepository extends JpaRepository<CatJornada, Integer>, JpaSpecificationExecutor  {
}
