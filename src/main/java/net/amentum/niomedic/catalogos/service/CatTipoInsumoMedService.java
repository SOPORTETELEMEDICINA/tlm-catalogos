package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatTipoInsumoMedException;
import net.amentum.niomedic.catalogos.views.CatTipoInsumoMedView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatTipoInsumoMedService {
   CatTipoInsumoMedView getDetailsByIdCatTipoInsumoMed(Integer idCatTipoInsumoMed) throws CatTipoInsumoMedException;

   List<CatTipoInsumoMedView> findAll() throws CatTipoInsumoMedException;

   Page<CatTipoInsumoMedView> getCatTipoInsumoMedSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipoInsumoMedException;
}
