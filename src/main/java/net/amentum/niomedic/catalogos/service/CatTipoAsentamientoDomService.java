package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatTipoAsentamientoDomException;
import net.amentum.niomedic.catalogos.views.CatTipoAsentamientoDomView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatTipoAsentamientoDomService {
   CatTipoAsentamientoDomView getDetailsByIdCatTipoAsentamientoDom(Integer idCatTipoAsentamientoDom) throws CatTipoAsentamientoDomException;

   List<CatTipoAsentamientoDomView> findAll() throws CatTipoAsentamientoDomException;

   Page<CatTipoAsentamientoDomView> getCatTipoAsentamientoDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipoAsentamientoDomException;
}
