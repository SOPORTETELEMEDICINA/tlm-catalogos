package net.amentum.niomedic.catalogos.service;


import net.amentum.niomedic.catalogos.exception.CatJornadaException;
import net.amentum.niomedic.catalogos.views.CatJornadaView;
import org.springframework.data.domain.Page;

import java.util.List;
public interface CatJornadaService {
    CatJornadaView getDetailsByjorid(Integer jorid) throws CatJornadaException;

    List<CatJornadaView> findAll() throws CatJornadaException;

    Page<CatJornadaView> getCatJornadaSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatJornadaException;

}
