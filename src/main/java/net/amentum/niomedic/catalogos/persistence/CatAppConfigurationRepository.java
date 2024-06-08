package net.amentum.niomedic.catalogos.persistence;

import net.amentum.niomedic.catalogos.model.CatAppConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface CatAppConfigurationRepository extends JpaRepository<CatAppConfiguration, Long>, JpaSpecificationExecutor {

    CatAppConfiguration findByidCliente(@NotNull Integer idCliente);

}
