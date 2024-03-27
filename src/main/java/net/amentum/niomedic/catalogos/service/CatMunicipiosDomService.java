package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatMunicipiosDomException;
import net.amentum.niomedic.catalogos.views.CatMunicipiosDomView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatMunicipiosDomService {
   CatMunicipiosDomView getDetailsByIdCatMunicipiosDom(Integer idCatMunicipiosDom) throws CatMunicipiosDomException;

   List<CatMunicipiosDomView> findAll() throws CatMunicipiosDomException;

   Page<CatMunicipiosDomView> getCatMunicipiosDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatMunicipiosDomException;
}
