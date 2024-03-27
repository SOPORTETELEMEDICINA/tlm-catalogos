package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatCluesException;
import net.amentum.niomedic.catalogos.views.CatCluesView;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.List;


public interface CatCluesService {

    CatCluesView getDetailsByidCatClues(Integer idCatClues) throws CatCluesException;

    List<CatCluesView> findAll() throws CatCluesException;

    Page<CatCluesView> getCatCluesSearch(String fkEntidad, String claveInstitucion) throws CatCluesException;



}
