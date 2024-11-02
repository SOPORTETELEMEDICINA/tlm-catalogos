package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatEspecialidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatEspecialidadesRepository extends JpaRepository<CatEspecialidades, Integer>, JpaSpecificationExecutor {
    // Método para obtener sólo las especialidades activas
    @Query("SELECT c FROM CatEspecialidades c WHERE c.activo = true")
    List<CatEspecialidades> findAllActive();
}
