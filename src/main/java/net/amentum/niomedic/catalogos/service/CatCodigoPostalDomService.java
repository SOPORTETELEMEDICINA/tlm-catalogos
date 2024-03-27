package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatCodigoPostalDomException;
import net.amentum.niomedic.catalogos.views.CatCodigoPostalDomView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatCodigoPostalDomService {
   CatCodigoPostalDomView getDetailsByIdCatCodigoPostalDom(Long idCatCodigoPostalDom) throws CatCodigoPostalDomException;

   List<CatCodigoPostalDomView> findAll() throws CatCodigoPostalDomException;

   Page<CatCodigoPostalDomView> getCatCodigoPostalDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatCodigoPostalDomException;
}
