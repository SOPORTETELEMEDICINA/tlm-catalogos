package net.amentum.niomedic.catalogos.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.amentum.niomedic.catalogos.model.CatEstudios;

@Repository
public interface CatEstudiosRepository extends JpaRepository<CatEstudios, Integer>, JpaSpecificationExecutor<CatEstudios>{

}
