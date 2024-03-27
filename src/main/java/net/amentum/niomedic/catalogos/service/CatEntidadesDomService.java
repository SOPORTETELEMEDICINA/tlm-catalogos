package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatEntidadesDomException;
import net.amentum.niomedic.catalogos.views.CatEntidadesDomView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatEntidadesDomService {
   CatEntidadesDomView getDetailsByIdCatEntidadesDom(Integer idCatEntidadesDom) throws CatEntidadesDomException;

   List<CatEntidadesDomView> findAll() throws CatEntidadesDomException;

   Page<CatEntidadesDomView> getCatEntidadesDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatEntidadesDomException;
}
