package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatAreatrabajoException;
import net.amentum.niomedic.catalogos.views.CatAreatrabajoView;
import org.springframework.data.domain.Page;

import java.util.List;
public interface CatAreatrabajoService {

    CatAreatrabajoView getDetailsByatrid(Integer atrid) throws CatAreatrabajoException;

    List<CatAreatrabajoView> findAll() throws CatAreatrabajoException;

    Page<CatAreatrabajoView> getCatAreatrabajoSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatAreatrabajoException;

}
