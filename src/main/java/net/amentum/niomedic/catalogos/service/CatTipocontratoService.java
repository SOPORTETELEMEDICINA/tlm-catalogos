package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatTipocontratoException;
import net.amentum.niomedic.catalogos.views.CatTipocontratoView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatTipocontratoService {

    CatTipocontratoView getDetailsByconid(Integer conid) throws CatTipocontratoException;

    List<CatTipocontratoView> findAll() throws CatTipocontratoException;

    Page<CatTipocontratoView> getCatTipocontratoSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipocontratoException;

}
