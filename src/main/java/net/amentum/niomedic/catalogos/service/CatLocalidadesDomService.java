package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatLocalidadesDomException;
import net.amentum.niomedic.catalogos.views.CatLocalidadesDomView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatLocalidadesDomService {
   CatLocalidadesDomView getDetailsByIdCatLocalidadesDom(Integer idCatLocalidadesDom) throws CatLocalidadesDomException;

   List<CatLocalidadesDomView> findAll() throws CatLocalidadesDomException;

   Page<CatLocalidadesDomView> getCatLocalidadesDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatLocalidadesDomException;
}
