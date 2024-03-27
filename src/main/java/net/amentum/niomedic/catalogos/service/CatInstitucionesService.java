package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatInstitucionesException;
import net.amentum.niomedic.catalogos.views.CatInstitucionesView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatInstitucionesService {
    CatInstitucionesView getDetailsByidinstitucion(Integer idinstitucion) throws CatInstitucionesException;

    List<CatInstitucionesView> findAll() throws CatInstitucionesException;

    Page<CatInstitucionesView> getCatInstitucionesSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatInstitucionesException;


}
