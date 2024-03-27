package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatPersonalmedicoException;
import net.amentum.niomedic.catalogos.views.CatPersonalmedicoView;
import org.springframework.data.domain.Page;

import java.util.List;
public interface CatPersonalmedicoService {
    CatPersonalmedicoView getDetailsByperid(Integer perid) throws CatPersonalmedicoException;

    List<CatPersonalmedicoView> findAll() throws CatPersonalmedicoException;

    Page<CatPersonalmedicoView> getCatPersonalmedicoSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatPersonalmedicoException;



}
