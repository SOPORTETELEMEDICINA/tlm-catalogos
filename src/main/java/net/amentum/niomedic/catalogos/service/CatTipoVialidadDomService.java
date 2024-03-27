package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatTipoVialidadDomException;
import net.amentum.niomedic.catalogos.views.CatTipoVialidadDomView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatTipoVialidadDomService {
   CatTipoVialidadDomView getDetailsByIdCatTipoVialidadDom(Integer idCatTipoVialidadDom) throws CatTipoVialidadDomException;

   List<CatTipoVialidadDomView> findAll() throws CatTipoVialidadDomException;

   Page<CatTipoVialidadDomView> getCatTipoVialidadDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipoVialidadDomException;
}
